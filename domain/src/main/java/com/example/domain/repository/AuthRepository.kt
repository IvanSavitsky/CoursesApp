package com.example.domain.repository

interface AuthRepository {
    fun write(value: String)
    fun read(defaultValue: String): String
}