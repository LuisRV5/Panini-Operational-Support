package com.example.panini.ui.screens

import androidx.lifecycle.ViewModel
import com.example.panini.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState(
                hasError = true, 
                errorMessage = "Por favor, completa todos los campos."
            )
            return
        }

        val success = repository.login(email, password)
        if (success) {
            _uiState.value = AuthUiState(isAuthenticated = true, hasError = false)
        } else {
            _uiState.value = AuthUiState(
                isAuthenticated = false,
                hasError = true,
                errorMessage = "Credenciales incorrectas. Inténtalo de nuevo."
            )
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState()
    }
}
