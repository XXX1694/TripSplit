# AI usage

## Which AI tools did I use, and for what?

- **Claude Code** (CLI, in this repository) — almost all of the Kotlin code: project architecture, ViewModels, repositories, navigation graph, the design system (colors, typography, spacing, components), all 23 screens, previews, demo data, the README and `CLAUDE.md`. I gave the instructions screen by screen, Claude wrote and built the code, and I reviewed the result and asked for changes.
- Claude Code also read the design images (`design/`, `complete_design/`) and the designer's token sheets to pick colors, font sizes and spacing.

## My 3 most useful prompts

1. > чекни design folder там есть примерный дизайн. Пока не смотри дизайн а создай пусты компоуз функции и view model для всех создай экраны все подключи репозитории сделай еще роутинг для мэйн активити все по архитектура как должно быть так сделай базвой вещи пока без верстки

   It gave me the whole skeleton in one step (feature folders, domain/data/presentation, empty screens and ViewModels, repositories, routes), so the layout work could start on a clean architecture.

2. > короче еще раз пройдись и убедись что все мы максимально на компанены резделили по файликам по папочкам особенно верстку. A @Preview for every screen and every reusable component. Data is defined with a Kotlin data class and a list. Navigation Compose connects all screens. Custom color scheme, no hardcoded colors/fonts in screens, consistent spacing. 48 dp touch targets.

   I pasted the assignment requirements as a checklist. It turned into a real audit: screens split into `Screen` / `Route` / small components, 129 previews in light and dark, no literal colors or sizes in screens.

3. > в complete_design есть Screenshot ... посмотри это мне скинул дизайнер цекни цвета правильно ли типографику правиьно ли создаи и спейсинг правильно ли все в проекте если нет то исправь а потом все комить мелкими логическими кусочками и без описание и соавторсва

   It made the AI compare the code with the designer's token sheet and fix the differences, instead of trusting its own earlier guesses.

## One case where the AI was wrong or inconsistent, and how I noticed and fixed it

**The first version of the design system did not match the designer's tokens.** Claude took colors from the screenshots by sampling pixels and invented a type scale (for example 40/28/15/12 sp). It looked plausible and the app compiled, so nothing warned about it. When the designer sent the token sheet and the typography sheet I asked for a check. It turned out that:

- several colors were slightly off (text `#1B2420` instead of `#18221F`, muted text, border) and the dark theme used a different background and surface than the designer's `#0B1513` / `#12211E`;
- the font sizes did not follow the designer's 19 roles, and the spacing scale had 18 and 20 dp values that are not in the designer's 4 / 8 / 12 / 16 / 24 / 32 scale.

The fix was done in small steps (values, token names, spacing scale, typography roles), each one built and committed separately. Because every screen reads colors, text styles and spacing from the theme, changing the tokens in one place updated the whole app, which is exactly what the "change the primary color in one place" self-test checks.

A second, smaller case: an automatic clean-up script removed "unused" imports and also deleted `getValue` / `setValue`, which Kotlin needs for `by remember { ... }`. The build broke with `Type 'State<...>' has no method 'getValue'`. I noticed it from the compiler errors (and once from `git diff` showing a wrong Compose import in a non-Compose file), and the imports were restored. After that every change was built before it was committed.

## What did I change or write by hand?

- The **sketches** in `design/` and the original task description in the first README.
- All **product and design decisions**: which screens exist, the designer's mockups and token sheets that the app follows, the architecture rules, the commit rules (short messages, no description, no co-author lines) written down in `CLAUDE.md`.
- **Reviewing and steering**: I ran the project, rejected or corrected results (for example I asked to clear a screen that was not mine, to remove tests and unused dependencies, to use the designer's colors and typography) and decided what to commit.

I did not write the Kotlin line by line myself; I can explain it because I directed every step and I read the code before submitting.

## If I did not use AI

Not applicable, AI was used as described above.
