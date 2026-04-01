package com.example.nav.validation

import android.util.Patterns
import com.example.nav.model.ValidationResult

class RegisterValidator {

    fun validateFullName(fullName: String): ValidationResult {
        val value = fullName.trim()
        return when {
            value.isEmpty() -> ValidationResult(false, "El nombre completo es obligatorio")
            value.length < 3 -> ValidationResult(false, "El nombre es demasiado corto")
            value.length > 50 -> ValidationResult(false, "El nombre es demasiado largo")
            else -> ValidationResult(true)
        }
    }

    fun validateEmail(email: String): ValidationResult {
        val value = email.trim()
        return when {
            value.isEmpty() -> ValidationResult(false, "El correo es obligatorio")
            value.length < 5 -> ValidationResult(false, "Mínimo 5 caracteres")
            value.length > 100 -> ValidationResult(false, "Máximo 100 caracteres")
            !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> ValidationResult(
                false,
                "Formato de correo inválido"
            )
            else -> ValidationResult(true)
        }
    }

    fun validateUsername(username: String): ValidationResult {
        val value = username.trim()
        return when {
            value.isEmpty() -> ValidationResult(false, "El usuario es obligatorio")
            value.length < 5 -> ValidationResult(false, "Mínimo 5 caracteres")
            value.length > 50 -> ValidationResult(false, "Máximo 50 caracteres")
            else -> ValidationResult(true)
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult(false, "La contraseña es obligatoria")
            password.length < 8 -> ValidationResult(false, "Mínimo 8 caracteres")
            password.length > 64 -> ValidationResult(false, "Máximo 64 caracteres")
            else -> ValidationResult(true)
        }
    }

    fun validateConfirmPassword(password: String, confirm: String): ValidationResult {
        return when {
            confirm.isEmpty() -> ValidationResult(false, "Debes confirmar la contraseña")
            password != confirm -> ValidationResult(false, "Las contraseñas no coinciden")
            confirm.length < 8 -> ValidationResult(false, "Mínimo 8 caracteres")
            confirm.length > 64 -> ValidationResult(false, "Máximo 64 caracteres")
            else -> ValidationResult(true)
        }
    }

    fun validateTerms(accepted: Boolean): ValidationResult {
        return if (!accepted) {
            ValidationResult(false, "Debes aceptar los términos")
        } else {
            ValidationResult(true)
        }
    }
}