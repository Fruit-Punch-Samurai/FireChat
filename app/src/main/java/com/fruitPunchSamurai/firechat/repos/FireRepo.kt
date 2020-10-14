package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.DocumentSnapshot

class FireRepo {

    private var usersRepo = FireUsersRepo()

    suspend fun getAllUsers() = usersRepo.getAllUsers()

    suspend fun addUser(user: User) {
        usersRepo.addUser(user)
    }

    suspend fun getUser(id: String?): DocumentSnapshot = usersRepo.getUser(id)


}