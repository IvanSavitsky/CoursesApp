package com.example.coursesapp.domain.auth

interface AuthRepository {
    fun write(value: String)
    fun read(defaultValue: String): String
}