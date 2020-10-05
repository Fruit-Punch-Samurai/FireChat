package com.fruitPunchSamurai.firechat.repos

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

object AuthRepo {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isLoggedIn() = auth.currentUser != null

    fun logOut() = auth.signOut()

    suspend fun signIn(email: String, password: String): AuthResult? {
        return auth.signInWithEmailAndPassword(email, password).await()
    }
}