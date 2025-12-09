package com.example.coursesapp.presentation.login

import androidx.lifecycle.ViewModel
import com.example.coursesapp.domain.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val isValid = MutableStateFlow(false)

    fun checkValidation(email: String, password: String) {
        if (authRepository.checkValidation(email, password)) {
            authRepository.login()
            isValid.value = true
        }
    }
}