package com.fruitPunchSamurai.firechat.repos

import android.content.Context
import android.net.Uri
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

    fun setLastMessageAsRead(currentUserID: String, contactID: String) =
        fireRepo.setLastMessageAsRead(currentUserID, contactID)


    suspend fun getImage(ctx: Context, mediaID: String, receiverID: String) =
        fireRepo.getImage(ctx, mediaID, authRepo.getUID()!!, receiverID)

    suspend fun addTextMessageAndLastMessage(
        message: Message,
        lastMessage: LastMessage,
    ) {
        if (!message.typeIsText()) return

        fireRepo.addTextMessageAndLastMessage(
            message,
            lastMessage,
            authRepo.getUsername()!!
        )
    }

    suspend fun addImageMessageAndLastMessage(
        message: Message,
        lastMessage: LastMessage,
        uri: Uri
    ) {
        if (!message.typeIsImage()) return

        lastMessage.apply { msg = "Sent an attachment" }

        fireRepo.addImageMessageAndLastMessage(message, lastMessage, authRepo.getUsername()!!, uri)
    }

}