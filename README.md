# PokemonEgg

# Pokemon Eggs (Android)

An offline Android game (Jetpack Compose + Room) inspired by your Flask "Pokemon Eggs" app.

## Features
- Local trainer profile (on-device)
- Tap to train:
  - progresses all eggs by 1 tap
  - every 5 taps levels up the whole team (and triggers evolutions)
  - every 20 taps grants a new egg
- Hatch eggs once ready and optionally set a nickname
- Shiny chance (✨)
- Team screen with rename support
- Mini Pokedex (subset included; easy to extend)
- Achievements screen
- Daily login bonus egg

## Open in Android Studio
1. Unzip the project
2. Android Studio: File > Open > select the `PokemonEggsAndroid` folder
3. Let Gradle sync, then Run.

## Important note about Gradle wrapper
This ZIP does not include `gradle/wrapper/gradle-wrapper.jar` (Android Studio typically generates/repairs wrapper files).
If sync complains about a missing wrapper JAR:
- In Android Studio, open the Gradle tool window and run the `wrapper` task, or
- Create a new empty Compose project and copy its `gradle/wrapper/gradle-wrapper.jar` into this project.

## Extend the Pokemon list
Edit: `app/src/main/java/com/example/pokemoneggs/data/PokemonCatalog.kt`
