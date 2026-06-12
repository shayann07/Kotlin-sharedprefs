# Kotlin-sharedprefs

Educational Kotlin Android sample showing how to persist a small user record across app launches with `SharedPreferences`. The user object is serialized as JSON via Gson, and an `isLoggedIn` flag drives a one-shot auto-redirect from the launcher screen.

## What It Does

- **`MainActivity`** (launcher) — On start, if `isLoggedIn` is `true` it skips straight to `HomeActivity`. Otherwise it shows email/password fields and a "Sign In" button. The button compares the typed values against the stored `UserModel` (read back via Gson). On match it sets `isLoggedIn = true` and goes to `HomeActivity`; otherwise it shows an "Invalid credentials" toast. A "Sign Up" link opens `SignUpActivity`.
- **`SignUpActivity`** — Username/email/password fields and a "Sign Up" button. All three must be non-empty (only emptiness is checked). On submit it builds a `UserModel`, writes it via `SharedPrefs.saveUser`, sets `isLoggedIn = false`, and navigates back to `MainActivity` to log in.
- **`HomeActivity`** — Reads the stored user and shows `"WELCOME <username>!"`. The "Logout" button clears `SharedPreferences` and returns to `MainActivity`.

`SharedPrefs` (a regular class, not extension functions on `Context`) wraps the `MyPreferences` file with `MODE_PRIVATE` and exposes `saveUser`, `getUser`, `setIsLoggedIn`, `isLoggedIn`, and `clear`. Gson handles the `UserModel` ↔ JSON conversion.

## Project Layout

```
app/
  src/
    main/
      AndroidManifest.xml
      java/com/example/kotlin_sharedprefs/
        MainActivity.kt        # email/password sign-in + auto-redirect
        SignUpActivity.kt      # creates and stores the UserModel
        HomeActivity.kt        # welcome screen + logout
        SharedPrefs.kt         # Gson-backed SharedPreferences wrapper
        UserModel.kt           # data class (username, email, password)
      res/layout/
        activity_main.xml
        activity_sign_up.xml
        activity_home.xml
build.gradle.kts
settings.gradle.kts
```

## Tech Stack

- Kotlin, JVM target `11`.
- Android Gradle Plugin via `libs.versions.toml` (Kotlin DSL `build.gradle.kts`).
- `compileSdk 36`, `minSdk 24`, `targetSdk 36`.
- AndroidX `core-ktx`, `appcompat`, `activity`, `constraintlayout`, Material Components.
- `com.google.code.gson:gson:2.11.0` for JSON serialization.
- View Binding (`buildFeatures.viewBinding = true`).
- Namespace + applicationId: `com.example.kotlin_sharedprefs`.

## Build / Run

1. Open in Android Studio with AGP matching `gradle/libs.versions.toml`.
2. Sync Gradle and let it download dependencies.
3. Run on an emulator or device. The launcher activity is `MainActivity`.

## Tests

Only the default Android Studio stubs:

- `ExampleUnitTest.kt` — JUnit `addition_isCorrect`.
- `ExampleInstrumentedTest.kt` — instrumentation test asserting the package name.

There is no automated coverage of the auth flow or `SharedPrefs`.

## Honest Limitations

- This is a learning sample, not a production auth system.
- **The user's password is stored in clear text inside SharedPreferences.** SharedPreferences is not an appropriate place for credentials; use this only as a SharedPreferences/Gson teaching aid.
- The "extension functions" wording sometimes used in earlier docs is not accurate — `SharedPrefs` is a regular class with member functions.
- `HomeActivity` contains a bug: when no user record is found it calls `startActivity(Intent(this, HomeActivity::class.java))` instead of routing back to `MainActivity`, which restarts itself rather than navigating to login.
- `SignUpActivity` only validates that fields are non-empty; there is no email format, password strength, or duplicate-account check.
- `MainActivity.btnSignIn` does not handle the case where no user has been stored yet beyond the "Invalid credentials" branch.
- There is no `LICENSE` file in the repository despite earlier README text claiming MIT licensing. The license status is **unspecified**.

## License

No `LICENSE` file is present in the repository. Treat the licensing status as **unspecified** until one is added.
