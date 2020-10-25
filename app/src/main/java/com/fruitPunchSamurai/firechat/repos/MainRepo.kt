package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.ktx.toObject

class MainRepo {

    private val fireRepo = FireRepo()
    private val authRepo = AuthRepo()

    suspend fun addUser(user: User) {
        fireRepo.addUser(user)
    }

    suspend fun getUser(id: String?): User? {
        val snap = fireRepo.getUser(id)
        if (!snap.exists()) return null
        return snap.toObject<User>()
    }

    suspend fun addMessage(message: Message, currentUserID: String, receiverID: String) {
        fireRepo.addMessage(message, currentUserID, receiverID)
    }

    suspend fun addLastMessage(
        lastMessage: LastMessage,
        currentUserID: String,
        receiverID: String,
    ) {
        fireRepo.addLastMessage(lastMessage, currentUserID, receiverID, authRepo.getUsername()!!)
    }

    fun setLastMessageAsRead(currentUserID: String, contactID: String) {
        fireRepo.setLastMessageAsRead(currentUserID, contactID)
    }

}