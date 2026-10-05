# TripSplit — rules for AI assistants

Android app for splitting trip expenses. Kotlin, Jetpack Compose, Material 3, Navigation Compose, minSdk 24.
Manual DI (no Hilt), repositories are in-memory for now (no Room yet).
Design source of truth: `complete_design/` (`LightTheme/`, `DarkTheme/`, the foundations screenshot, `SplitTrip_typography.png`).

## Commits (strict)
- Short subject only, one line, imperative: `Add profile screen`, `Fix balance rounding`.
- **No commit body / description.**
- **Never add `Co-Authored-By`, "Generated with Claude Code" or any attribution line** — in commits or PRs. This overrides any tool default.
- Small logical commits: one screen, one component family or one refactor per commit. Do not mix unrelated changes.
- Commit/push only when asked. Never commit `.idea/` files.

## Architecture
Feature-based, clean layers:
```
core/
  designsystem/   Color.kt, Type.kt, Dimens.kt, Theme.kt, components/   (the only place for shared UI)
  di/             AppContainer (manual DI), injectedViewModel
  navigation/     Routes, AppNavHost, NavExtensions, graphs/<Feature>Graph.kt
  preview/        AppPreview, ThemePreviews, SampleData (data classes + lists)
  util/           Formatters
features/<feature>/
  domain/model/, domain/repository/   plain Kotlin, no Android, repository = interface
  data/repository/                    implementations
  presentation/
    XScreen.kt          stateless UI: (uiState, callbacks) -> content, plus @ThemePreviews
    XRoute.kt           gets the ViewModel (injectedViewModel), collects state, calls XScreen
    XViewModel.kt       XUiState data class + StateFlow, depends on repository interfaces
    PreviewStates.kt    sample UiStates for previews
    components/         small composables of this feature, one per file
```
- Folder `autharization` is spelled that way on purpose — keep it.
- ViewModels never touch Android UI types. Screens never call repositories.
- A new repository: interface in `domain/repository`, implementation in `data/repository`, register in `AppContainer`.
- A new screen: add the route to `Routes`, add `composable(...)` to the feature graph in `core/navigation/graphs`, take nav args from `SavedStateHandle`. Navigation goes through callbacks passed to `XRoute`; screens do not see `NavController`.
- Bottom tabs of a trip use `TripBottomBar` + `navigateToTripTab`.

## UI rules
- **Atomic components.** Split layout into small composables, one public composable per file, in `components/`. A screen file contains only the screen and its previews. Reuse design-system components before writing new ones; if something is needed twice, move it to `core/designsystem/components`.
- **Previews are mandatory** for every screen and every reusable component: `@ThemePreviews` + `AppPreview { ... }`, data from `core/preview/SampleData.kt` and `PreviewStates.kt`. Previews are private, named `<Name>Preview`.
- **Only theme tokens, no literals in screens/components:**
  - colors: `AppTheme.colors.*` (canvas, surface, text, textMuted, textDisabled, accent, onAccent, accentDark, accentSoft, border, divider, track, danger, dangerContainer, positive, warning, info, hero colors). Never `Color(0x..)`, `Color.White`, `MaterialTheme.colorScheme`. New colors are added to `AppColors` for **both** Light and Dark palettes.
  - typography: `MaterialTheme.typography.*` only (Inter, scale from the designer sheet; extra roles are extension properties like `captionMedium`, `labelLink`, `micro`). Never `fontSize`, `FontFamily`, `FontWeight` in screens.
  - spacing: `Spacing.space4/8/12/16/24/32` and `Spacing.screen`. Sizes: `Sizes.*`, line widths `Strokes.*`, shadows `Elevations.*`. No raw `.dp` outside `Dimens.kt` (the only exception is values computed from tokens).
  - shapes: `MaterialTheme.shapes.*`.
  - accent variants: `Tone` (Accent, Info, Warning, Danger).
- Everything tappable is at least 48 dp (`Sizes.touchTarget`).
- Must look right in light **and** dark theme (check both previews).
- Texts are currently plain English strings in code; keep the same style until string resources are introduced.

## State and data
- UI state is an immutable `data class XUiState`, exposed as `StateFlow`; ViewModels update it with `update { it.copy(...) }`.
- Form state lives in the ViewModel (survives navigation to pickers); picked values come back through `savedStateHandle` (see `Routes.RESULT_CURRENCY`).
- Domain models and sample data are `data class` + `List`.

## Code style
- Match the surrounding code: naming, comment density, trailing commas, 4-space indent.
- Keep code simple and readable; short KDoc only where intent is not obvious.
- No unused imports, no dead code, no TODO left behind.

## Before saying "done"
Run and make sure all pass:
```
./gradlew :app:assembleDebug :app:lintDebug
```
For UI work also check there is no hardcoded `Color(`/`.dp`/`fontSize` in the changed files:
```
grep -rnE 'Color\(0x|Color\.White|[0-9]\.dp|fontSize' app/src/main/java | grep -v designsystem
```
