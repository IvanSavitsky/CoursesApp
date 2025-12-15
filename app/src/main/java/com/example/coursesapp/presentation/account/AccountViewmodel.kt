package com.example.coursesapp.presentation.account

import androidx.lifecycle.ViewModel
import com.example.coursesapp.domain.auth.AuthRepository
import com.example.coursesapp.domain.auth.LogoutUseCase

class AccountViewmodel(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    fun logout() {
        logoutUseCase()
    }
}