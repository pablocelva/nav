package com.example.nav.model

data class LoginUIState (
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val loginErrorMessage: String? = null,
    val isLoginSuccess: Boolean = false
)