package com.example.plutoaccessories.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun createLoginSession(idUser: Int, nama: String?, email: String?, role: String?) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_ID, idUser)
        editor.putString(KEY_NAME, nama)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_ROLE, role)
        editor.commit()
    }

    val isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    val role: String
        get() = prefs.getString(KEY_ROLE, "user")!!

    val userId: Int
        get() = prefs.getInt(KEY_ID, -1)

    fun logout() {
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val PREF_NAME = "pluto_session"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_ID = "id_user"
        private const val KEY_NAME = "nama"
        private const val KEY_EMAIL = "email"
        private const val KEY_ROLE = "role"
    }
}