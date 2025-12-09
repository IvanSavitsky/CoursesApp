package com.example.coursesapp.domain.auth

interface AuthRepository {
    fun isAuthorized(): Boolean
    fun login()
    fun logout()
    fun checkValidation(email: String, password: String): Boolean
}