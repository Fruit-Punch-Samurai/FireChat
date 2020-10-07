package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FireUsersRepo {

    enum class FIELDS(val field: String) {
        NAME("name")
    }

    private val fire = Firebase.firestore
    private val usersColl = fire.collection("Users")


    suspend fun addUser(user: User) {
        val data = hashMapOf(
            FIELDS.NAME.field to user.name,
        )
        usersColl.document(user.id).set(data, SetOptions.merge()).await()
    }
}