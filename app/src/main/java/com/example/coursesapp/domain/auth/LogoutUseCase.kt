package com.example.coursesapp.domain.auth

class LogoutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.write(value = "false")
}