package com.example.nav.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav.model.LoginValidator
import com.example.nav.model.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val loginValidator: LoginValidator = LoginValidator()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun onUsernameChanged(username: String) {
        val usernameValidation = loginValidator.validateUsername(username)
        val currentPassword = _uiState.value.password
        val passwordValidation = loginValidator.validatePassword(currentPassword)

        _uiState.value = _uiState.value.copy(
            username = username,
            usernameError = usernameValidation.errorMessage,
            isFormValid = usernameValidation.isValid && passwordValidation.isValid
        )
    }

    fun onPasswordChanged(password: String) {
        val passwordValidation = loginValidator.validatePassword(password)
        val currentUsername = _uiState.value.username
        val usernameValidation = loginValidator.validateUsername(currentUsername)

        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = passwordValidation.errorMessage,
            isFormValid = usernameValidation.isValid && passwordValidation.isValid
        )
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentState = _uiState.value

        val usernameValidation = loginValidator.validateUsername(currentState.username)
        val passwordValidation = loginValidator.validatePassword(currentState.password)

        val formValid = usernameValidation.isValid && passwordValidation.isValid

        _uiState.value = currentState.copy(
            usernameError = usernameValidation.errorMessage,
            passwordError = passwordValidation.errorMessage,
            isFormValid = formValid
        )

        if (!formValid) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Llamada use case real
                //authUseCase.login(currentState.username,trim(), currentState.password)

                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess()
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                onError("Credenciales inválidas")
            }
        }
    }
}
