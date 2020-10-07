package com.fruitPunchSamurai.firechat.repos

import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object AuthRepo {

    private val auth = Firebase.auth

    fun isLoggedIn() = auth.currentUser != null

    fun logOut() = auth.signOut()

    fun getUsername() = if (isLoggedIn()) auth.currentUser!!.displayName else null

    @Throws(FirebaseAuthInvalidUserException::class, FirebaseAuthInvalidCredentialsException::class)
    suspend fun signIn(email: String, password: String): AuthResult =
        auth.signInWithEmailAndPassword(email, password).await()


    @Throws(
        FirebaseAuthWeakPasswordException::class,
        FirebaseAuthInvalidCredentialsException::class,
        FirebaseAuthUserCollisionException::class
    )

    suspend fun signUp(email: String, password: String): AuthResult =
        auth.createUserWithEmailAndPassword(email, password).await()

    @Throws(FirebaseAuthInvalidUserException::class)
    suspend fun setUsername(name: String) {
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates).await()
    }
}