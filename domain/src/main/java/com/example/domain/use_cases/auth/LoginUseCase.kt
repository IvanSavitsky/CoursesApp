package com.example.domain.use_cases.auth

import com.example.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.write(value = "true")
}