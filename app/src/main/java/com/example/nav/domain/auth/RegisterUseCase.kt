package com.example.nav.domain.auth

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, displayName: String): AuthResult {
        return authRepository.register(email, password, displayName)
    }
}