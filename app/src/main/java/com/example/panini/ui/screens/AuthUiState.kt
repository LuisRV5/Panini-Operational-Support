package com.example.panini.ui.screens

data class AuthUiState(
    val isAuthenticated: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String = ""
)
