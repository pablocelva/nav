package com.example.nav.model

data class ValidationResult (
    val isValid: Boolean,
    val errorMessage: String? = null
)