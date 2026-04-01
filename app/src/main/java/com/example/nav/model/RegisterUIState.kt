package com.example.nav.model

data class RegisterUIState(
    val fullName: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val termsAccepted: Boolean = false,
    
    val fullNameError: String? = null,
    val emailError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val termsError: String? = null,
    
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,

    val isRegisterSuccess: Boolean = false,
    val registerErrorMessage: String? = null
)
