package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.ktx.toObject

class MainRepo {

    private val fireRepo = FireRepo()

    suspend fun addUser(user: User) {
        fireRepo.addUser(user)
    }

    suspend fun getUser(id: String?): User? {
        val snap = fireRepo.getUser(id)
        if (!snap.exists()) return null
        return snap.toObject<User>()
    }
}