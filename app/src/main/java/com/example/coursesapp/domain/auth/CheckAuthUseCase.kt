package com.example.coursesapp.domain.auth

class CheckAuthUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.read(defaultValue = "false").toBoolean()
}