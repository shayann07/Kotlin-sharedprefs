package com.example.kotlin_sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

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

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}