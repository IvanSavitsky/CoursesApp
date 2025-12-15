package com.example.coursesapp.domain.auth

class CheckValidationUseCase {
    operator fun invoke(
        email: String,
        password: String
    ): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()

        return !email.isEmpty() && !password.isEmpty() && emailRegex.matches(email)
    }
}