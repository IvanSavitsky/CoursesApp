package com.example.data

sealed interface LoadingState<out T> {
    data object Idle : LoadingState<Nothing>
    data class Loaded<T>(val value: T) : LoadingState<T>
    data object Loading : LoadingState<Nothing>
    data class Error(val throwable: Throwable) : LoadingState<Nothing>
}