package com.example.coursesapp.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.coursesapp.domain.auth.AuthRepository

class AuthRepositoryImpl(val sharedPreferences: SharedPreferences) : AuthRepository {
    private val prefName = "isAuthorized"
    override fun isAuthorized(): Boolean {
        return sharedPreferences.getString(prefName, "false").toBoolean()
    }

    override fun login() {
        sharedPreferences.edit {
            putString(prefName, "true")
        }
    }

    override fun logout() {
        sharedPreferences.edit {
            putString(prefName, "false")
        }
    }

    override fun checkValidation(
        email: String,
        password: String
    ): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()

        if (!email.isEmpty() && !password.isEmpty() && emailRegex.matches(email)) {
            return true
        } else {
            return false
        }
    }
}