package com.example.coursesapp.presentation.account

import androidx.lifecycle.ViewModel
import com.example.coursesapp.domain.auth.AuthRepository

class AccountViewmodel(private val authRepository: AuthRepository) : ViewModel() {
    fun logout() {
        authRepository.logout()
    }
}