package com.maou.reqresapp.data.source.local

import android.content.SharedPreferences

open class AppSharedPrefs(private val prefs: SharedPreferences) {

    fun saveToken(token: String) {
        prefs.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun fetchToken(): String? {
        return prefs.getString(ACCESS_TOKEN, null)
    }

    fun deleteToken() {
        prefs.edit().putString(ACCESS_TOKEN, null).apply()
    }

    companion object {
        const val APP_SHARED_PREFS = "APP_SHARED_PREFS"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }
}