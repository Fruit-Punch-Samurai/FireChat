package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FireUsersRepo {

    private val fire = Firebase.firestore
    private val usersColl = fire.collection("Users")

    companion object FIELDS {
        const val USERS_COLL = "Users"
    }

    suspend fun addUser(user: User) {
        usersColl.document(user.id).set(user).await()
    }

    suspend fun getUser(id: String?): DocumentSnapshot =
        usersColl.document(id.toString()).get().await()

}