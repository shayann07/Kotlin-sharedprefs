# Android SharedPreferences & Gson Object Persistence

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Language](https://img.shields.io/badge/Kotlin-2.0+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Serialization](https://img.shields.io/badge/Serialization-Google%20Gson%202.11.0-4285F4?style=for-the-badge&logo=google)](https://github.com/google/gson)
[![Storage](https://img.shields.io/badge/Storage-Android%20SharedPreferences-009688?style=for-the-badge&logo=android)](https://developer.android.com/training/data-storage/shared-preferences)
[![UI](https://img.shields.io/badge/UI-ViewBinding-FF4081?style=for-the-badge&logo=android)](https://developer.android.com/topic/libraries/view-binding)
[![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)](LICENSE)

> A clean, modular Android starter illustrating how to serialize and persist complex Kotlin data models into `SharedPreferences` using Google Gson, featuring a complete local authentication and session lifecycle.

---

## 📖 Overview

The **Kotlin-sharedprefs** project demonstrates a clean architectural approach for storing structured object data locally without requiring heavy SQLite or Room database setups. By combining Android's lightweight **SharedPreferences** key-value storage with **Google Gson** JSON serialization, complex Kotlin data classes can be persisted, retrieved, and updated with type safety.

### 🎯 Key Learning Objectives
- **Object-to-JSON Serialization**: Converting rich Kotlin data classes (`UserModel`) to JSON strings using `Gson().toJson()`.
- **JSON-to-Object Deserialization**: Reconstructing strongly typed objects from persisted strings via `Gson().fromJson()`.
- **Encapsulated Preferences Wrapper**: Isolating storage keys, serialization logic, and session flags inside a dedicated `SharedPrefs` manager class.
- **Local Authentication Lifecycle**: Managing user registration, credential verification, persistent session state (`isLoggedIn`), and complete cache wiping upon logout.
- **Edge-to-Edge UI with ViewBinding**: Utilizing AndroidX ViewBinding for null-safe layout binding and modern edge-to-edge system bar insets.

---

## 🏗️ Architecture & Storage Flow

```mermaid
graph TD
    classDef ui fill:#2D3748,stroke:#4FD1C5,stroke-width:2px,color:#fff;
    classDef manager fill:#1A365D,stroke:#63B3ED,stroke-width:2px,color:#fff;
    classDef gson fill:#7B341E,stroke:#ED8936,stroke-width:2px,color:#fff;
    classDef prefs fill:#744210,stroke:#F6E05E,stroke-width:2px,color:#fff;

    subgraph Activities ["Activity Layer (ViewBinding)"]
        MainActivity["MainActivity<br/>(Login Screen)"]:::ui
        SignUpActivity["SignUpActivity<br/>(Register Screen)"]:::ui
        HomeActivity["HomeActivity<br/>(Welcome Screen)"]:::ui
    end

    subgraph StorageWrapper ["SharedPrefs Manager"]
        SharedPrefsClass["SharedPrefs(context)"]:::manager
    end

    subgraph SerializationEngine ["Google Gson Engine"]
        GsonInstance["Gson()"]:::gson
    end

    subgraph LocalStorage ["Android SharedPreferences ('MyPreferences')"]
        KeyUser["KEY_USER ('user_data' -> JSON String)"]:::prefs
        KeyLogin["IS_LOGGED_IN ('isLoggedIn' -> Boolean)"]:::prefs
    end

    SignUpActivity -->|saveUser(UserModel)| SharedPrefsClass
    MainActivity -->|getUser() / isLoggedIn()| SharedPrefsClass
    HomeActivity -->|getUser() / clear()| SharedPrefsClass

    SharedPrefsClass -->|toJson(user)| GsonInstance
    GsonInstance -->|Writes JSON| KeyUser

    KeyUser -->|Reads JSON| GsonInstance
    GsonInstance -->|fromJson(json, UserModel)| SharedPrefsClass

    SharedPrefsClass -->|Writes Boolean| KeyLogin
```

---

## ✨ Core Concepts & Code Walkthrough

### 1. Encapsulated Storage Manager (`SharedPrefs.kt`)
A clean wrapper decoupling Android storage primitives from UI logic:
```kotlin
class SharedPrefs(context: Context) {
    private val PREF_NAME = "MyPreferences"
    private val KEY_USER = "user_data"
    private val IS_LOGGED_IN = "isLoggedIn"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: UserModel) {
        val json = gson.toJson(user)
        sharedPreferences.edit().putString(KEY_USER, json).apply()
    }

    fun getUser(): UserModel? {
        val json = sharedPreferences.getString(KEY_USER, null)
        return if (json != null) {
            gson.fromJson(json, UserModel::class.java)
        } else {
            null
        }
    }

    fun setIsLoggedIn(loggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, loggedIn).apply()
    }

    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(IS_LOGGED_IN, false)

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
```

### 2. Immutable Data Model (`UserModel.kt`)
Clean Kotlin data class holding account credentials:
```kotlin
data class UserModel(
    val username: String,
    val email: String,
    val password: String
)
```

### 3. Session Verification & Auto-Routing
`MainActivity` checks `sharedPrefs.isLoggedIn()` during creation to automatically bypass the login screen when an active session exists.

---

## 📱 Key Components & Project Structure

```
Kotlin-sharedprefs/
├── app/
│   ├── src/main/java/com/example/kotlin_sharedprefs/
│   │   ├── UserModel.kt                   # Kotlin Data class for user entity
│   │   ├── SharedPrefs.kt                 # SharedPreferences & Gson manager
│   │   ├── MainActivity.kt                # Login Activity & session validation
│   │   ├── SignUpActivity.kt              # User registration & object saving
│   │   └── HomeActivity.kt                # Welcome screen with data display & logout
│   ├── src/main/res/
│   │   ├── layout/                        # XML layouts (activity_main, activity_sign_up, activity_home)
│   │   └── values/                        # Colors, styles, and string resources
│   └── build.gradle.kts                   # Target SDK 36, dependencies & ViewBinding
└── gradle/
    └── libs.versions.toml                 # Version catalog
```

---

## 🛠️ Technology Stack Matrix

| Component | Technology | Version / Role |
|---|---|---|
| **Platform** | Android | API 24+ (Compile & Target SDK 36) |
| **Language** | Kotlin | 2.0+ |
| **Serialization** | Google Gson | `com.google.code.gson:gson:2.11.0` |
| **Storage Engine** | SharedPreferences | Local key-value persistent storage |
| **UI Framework** | Android XML + ViewBinding | Type-safe view interaction |
| **Design** | Material Components | `com.google.android.material:material` |

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio Ladybug (2024.2.1+)** or newer.
- **JDK 11 or JDK 17**.
- **Android SDK 36**.

### Build & Run
1. **Clone the repository**:
   ```bash
   git clone https://github.com/shayann07/Kotlin-sharedprefs.git
   cd Kotlin-sharedprefs
   ```
2. **Open in Android Studio**: Allow Gradle to synchronize dependencies.
3. **Build APK**:
   ```bash
   ./gradlew assembleDebug
   ```
4. **Run**: Launch on an emulator or connected Android device.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE) — Copyright (c) 2026 [shayann07](https://github.com/shayann07).
