package com.example.nav.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav.model.RegisterUIState
import com.example.nav.validation.RegisterValidator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel(
    private val validator: RegisterValidator = RegisterValidator(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState: StateFlow<RegisterUIState> = _uiState.asStateFlow()

    fun onFullNameChanged(fullName: String) {
        _uiState.value = _uiState.value.copy(
            fullName = fullName,
            fullNameError = validator.validateFullName(fullName).errorMessage
        )
        validateForm()
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = validator.validateEmail(email).errorMessage
        )
        validateForm()
    }

    fun onUsernameChanged(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            usernameError = validator.validateUsername(username).errorMessage
        )
        validateForm()
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = validator.validatePassword(password).errorMessage
        )
        validateForm()
    }

    fun onConfirmPasswordChanged(confirm: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirm,
            confirmPasswordError = validator.validateConfirmPassword(_uiState.value.password, confirm).errorMessage
        )
        validateForm()
    }

    fun onTermsChanged(accepted: Boolean) {
        _uiState.value = _uiState.value.copy(
            termsAccepted = accepted,
            termsError = if (accepted) null else "Debes aceptar los términos"
        )
        validateForm()
    }

    private fun validateForm() {
        val state = _uiState.value
        val isValid = validator.validateFullName(state.fullName).isValid &&
                validator.validateEmail(state.email).isValid &&
                validator.validateUsername(state.username).isValid &&
                validator.validatePassword(state.password).isValid &&
                validator.validateConfirmPassword(state.password, state.confirmPassword).isValid &&
                validator.validateTerms(state.termsAccepted).isValid

        _uiState.value = _uiState.value.copy(isFormValid = isValid)
    }

    fun register(onSuccess: () -> Unit, onError: (String) -> Unit) {
        validateForm()
        if (!_uiState.value.isFormValid) return

        val state = _uiState.value

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Registro real con Firebase Auth
                val result = auth.createUserWithEmailAndPassword(state.email, state.password).await()
                
                // Actualizar el perfil del usuario con el nombre completo
                val profileUpdates = userProfileChangeRequest {
                    displayName = state.fullName
                }
                result.user?.updateProfile(profileUpdates)?.await()

                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                onError(e.localizedMessage ?: "Error al crear la cuenta")
            }
        }
    }
}
