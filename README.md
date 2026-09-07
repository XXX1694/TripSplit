# TripSplit

TripSplit is a native Android application for managing shared expenses during trips. It helps groups of friends track expenses, calculate balances, and simplify settling debts between participants.

## Functionality

1. User can create, edit, and delete a trip.
2. User can add, edit, and remove participants from a trip.
3. User can create, edit, and remove an expense with an amount, description, category, payer, and participants.
4. App persists trips, participants, and expenses locally.
5. App automatically calculates each participant's balance.
6. App displays a settlement screen showing who owes money to whom and how much.
7. App minimizes the number of payments required to settle all debts.
8. User can view the expense history for a trip.
9. App supports multiple currencies and converts expenses into the trip's base currency.
10. App displays a trip summary with total spending and spending by category.

## Current Folder Structure

The project was created using Android Studio's **Empty Activity** template.

```text
TripSplit/
├── app/
│   ├── src/
│   │   ├── androidTest/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/tripsplit/
│   │   │   │       └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   ├── mipmap/
│   │   │   │   ├── values/
│   │   │   │   └── xml/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── README.md
```

## Build and Run

### Requirements

* Android Studio
* JDK
* Android SDK
* Android emulator or physical Android device

### Run in Android Studio

1. Open the project in Android Studio.
2. Wait for Gradle synchronization to finish.
3. Start an Android emulator or connect an Android device.
4. Select the `app` configuration.
5. Click **Run**.

### Build from the command line

On macOS/Linux:

```bash
./gradlew build
```

On Windows:

```bash
gradlew.bat build
```

### Run tests

On macOS/Linux:

```bash
./gradlew test
```

On Windows:

```bash
gradlew.bat test
```
