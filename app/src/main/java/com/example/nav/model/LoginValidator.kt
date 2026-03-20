package com.example.nav.model

class LoginValidator {

    fun validateUsername(username: String): ValidationResult {
        val value = username.trim()

        return when {
            value.isEmpty() -> ValidationResult(false, "El usuario es obligatorio")
            value.length < 5 -> ValidationResult(false, "El nombre de usuario debe tener al menos 5 caracteres")
            value.length > 50 -> ValidationResult(false, "El nombre de usuario no puede tener más de 50 caracteres")
            else -> ValidationResult(true)
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "La contraseña es obligatoria")
            password.length < 8 -> ValidationResult(false, "La contraseña debe tener al menos 8 caracteres")
            password.length > 64 -> ValidationResult(false, "La contraseña no puede tener más de 64 caracteres")
            else -> ValidationResult(true)
        }
    }
}