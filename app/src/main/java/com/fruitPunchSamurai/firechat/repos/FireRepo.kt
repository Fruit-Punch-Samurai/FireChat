package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.DocumentSnapshot

class FireRepo {

    private var usersRepo = FireUsersRepo()
    private var messagesRepo = FireMessagesRepo()

    suspend fun getAllUsers() = usersRepo.getAllUsers()

    suspend fun addUser(user: User) {
        usersRepo.addUser(user)
    }

    suspend fun getUser(id: String?): DocumentSnapshot = usersRepo.getUser(id)

    suspend fun addMessage(message: Message, currentUserID: String, receiverID: String) {
        messagesRepo.addMessage(message, currentUserID, receiverID)
    }


}