package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.User

class MainRepo {

    private val fireRepo = FireRepo()

    suspend fun addUser(user: User) {
        fireRepo.addUser(user)
    }
}