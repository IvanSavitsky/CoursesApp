package com.example.coursesapp.presentation.account

import androidx.lifecycle.ViewModel
import com.example.domain.use_cases.auth.LogoutUseCase

class AccountViewmodel(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    fun logout() {
        logoutUseCase()
    }
}