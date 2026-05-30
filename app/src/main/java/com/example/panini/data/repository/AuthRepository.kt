package com.example.panini.data.repository

class AuthRepository {
    // Credenciales de prueba simuladas
    private val mockEmail = "admin@panini.com"
    private val mockPassword = "admin"

    fun login(email: String, password: String): Boolean {
        // En un escenario real aquí se llamaría a Retrofit (ApiService.login)
        return email == mockEmail && password == mockPassword
    }
}
