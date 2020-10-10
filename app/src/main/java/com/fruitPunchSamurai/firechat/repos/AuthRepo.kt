package com.fruitPunchSamurai.firechat.repos

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepo {

    private val auth = Firebase.auth

    fun getUID() = auth.currentUser?.uid

    fun isLoggedIn() = auth.currentUser != null

    fun logOut() = auth.signOut()

    /**Gets the username stored in Firebase Auth*/
    fun getUsername() = if (isLoggedIn()) auth.currentUser?.displayName else null

    fun getEmail() = if (isLoggedIn()) auth.currentUser?.email else null

    suspend fun signIn(email: String, password: String): AuthResult =
        auth.signInWithEmailAndPassword(email, password).await()

    suspend fun signUp(email: String, password: String): AuthResult =
        auth.createUserWithEmailAndPassword(email, password).await()

    suspend fun setUsername(name: String) {
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates).await()
    }
}