package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.User

class FireRepo {

    private val usersRepo = FireUsersRepo()

    suspend fun addUser(user: User) {
        usersRepo.addUser(user)
    }

}