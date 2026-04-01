package com.example.nav.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav.validation.LoginValidator
import com.example.nav.model.LoginUIState
import com.google.firebase.auth.FirebaseAuth
import com.example.nav.data.FirebaseAuthRepository
import com.example.nav.domain.auth.AuthResult
import com.example.nav.domain.auth.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val loginValidator = LoginValidator()
    private val repository = FirebaseAuthRepository(FirebaseAuth.getInstance())
    private val loginUseCase = LoginUseCase(repository)

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun onUsernameChanged(email: String) {
        val emailValidation = loginValidator.validateEmail(email)
        val currentPassword = _uiState.value.password
        val passwordValidation = loginValidator.validatePassword(currentPassword)

        _uiState.value = _uiState.value.copy(
            username = email,
            usernameError = emailValidation.errorMessage,
            loginErrorMessage = null,
            isFormValid = emailValidation.isValid && passwordValidation.isValid
        )
    }

    fun onPasswordChanged(password: String) {
        val passwordValidation = loginValidator.validatePassword(password)
        val currentEmail = _uiState.value.username
        val emailValidation = loginValidator.validateEmail(currentEmail)

        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = passwordValidation.errorMessage,
            loginErrorMessage = null,
            isFormValid = emailValidation.isValid && passwordValidation.isValid
        )
    }

    fun login() {
        val currentState = _uiState.value
        val email = currentState.username.trim()
        val password = currentState.password

        val emailValidation = loginValidator.validateEmail(email)
        val passwordValidation = loginValidator.validatePassword(password)

        val formValid = emailValidation.isValid && passwordValidation.isValid

        _uiState.value = currentState.copy(
            usernameError = emailValidation.errorMessage,
            passwordError = passwordValidation.errorMessage,
            loginErrorMessage = null,
            isFormValid = formValid
        )

        if (!formValid) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                loginErrorMessage = null,
                isLoginSuccess = false
            )

            when (val result = loginUseCase(email, password)) {
                is AuthResult.Success -> {
                    Log.d("LoginViewModel", "Login exitoso")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccess = true
                    )
                }

                is AuthResult.Error -> {
                    Log.d("LoginViewModel", "Error de autenticación: ${result.message}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loginErrorMessage = result.message,
                        isLoginSuccess = false
                    )
                }
            }
        }
    }

    fun clearLoginSuccess() {
        _uiState.value = _uiState.value.copy(isLoginSuccess = false)
    }

    fun clearLoginError() {
        _uiState.value = _uiState.value.copy(loginErrorMessage = null)
    }
}