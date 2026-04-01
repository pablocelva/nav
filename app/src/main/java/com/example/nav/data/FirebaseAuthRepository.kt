package com.example.nav.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.example.nav.domain.auth.AuthRepository
import com.example.nav.domain.auth.AuthResult
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email,password).await()
            AuthResult.Success
        } catch (_: Exception) {
            AuthResult.Error("Credenciales inválidas")
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        displayName: String
    ): AuthResult {
            return try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

                val profileUpdates = userProfileChangeRequest {
                    this.displayName = displayName
                }
                result.user?.updateProfile(profileUpdates)?.await()

                AuthResult.Success
            } catch (e: Exception) {
                AuthResult.Error(e.localizedMessage ?: "Error al crear la cuenta. Inténtalo de nuevo.")
            }
    }
}