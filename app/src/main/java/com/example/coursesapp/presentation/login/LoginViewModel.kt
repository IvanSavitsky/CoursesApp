package com.example.coursesapp.presentation.login

import androidx.lifecycle.ViewModel
import com.example.coursesapp.domain.auth.AuthRepository
import com.example.coursesapp.domain.auth.CheckValidationUseCase
import com.example.coursesapp.domain.auth.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel(
    private val checkValidationUseCase: CheckValidationUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val isValid = MutableStateFlow(false)

    fun checkValidation(email: String, password: String) {
        if (checkValidationUseCase(email, password)) {
            loginUseCase()
            isValid.value = true
        }
    }
}