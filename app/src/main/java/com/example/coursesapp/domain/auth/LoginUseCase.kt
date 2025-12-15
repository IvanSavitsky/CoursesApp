package com.example.coursesapp.domain.auth

class LoginUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.write(value = "true")
}