package com.example.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.domain.repository.AuthRepository

class AuthRepositoryImpl(val sharedPreferences: SharedPreferences) : AuthRepository {
    private val prefName = "isAuthorized"

    override fun write(value: String) {
        sharedPreferences.edit {
            putString(prefName, value)
        }
    }

    override fun read(defaultValue: String): String {
        return sharedPreferences.getString(prefName, defaultValue)!!
    }
}