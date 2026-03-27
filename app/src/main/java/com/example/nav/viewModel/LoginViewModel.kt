package com.example.nav.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav.model.LoginValidator
import com.example.nav.model.LoginUIState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel (
    private val loginValidator: LoginValidator = LoginValidator(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
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
                // Autenticación real con Firebase usando el campo "username" como email
                auth.signInWithEmailAndPassword(currentState.username, currentState.password).await()
                
                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                onError("Error de autenticación. Datos incorrectos")
                //onError(e.localizedMessage ?: "Error de autenticación")
            }
        }
    }
    
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
