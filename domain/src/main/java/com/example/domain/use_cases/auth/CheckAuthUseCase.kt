package com.example.domain.use_cases.auth

import com.example.domain.repository.AuthRepository

class CheckAuthUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.read(defaultValue = "false").toBoolean()
}