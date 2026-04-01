package com.example.nav.domain.auth

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult

    suspend fun register(email: String, password: String, displayName: String): AuthResult
}