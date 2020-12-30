package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.models.Message
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

internal class FireMessagesRepo {

    private val fire = Firebase.firestore
    private val messagesColl = fire.collection("Messages")
    private val lastMessagesColl = fire.collection(LAST_MESSAGES_COLL)

    companion object FIELDS {
        const val LAST_MESSAGES_COLL = "LastMessages"
        const val READ =
            "read" //read : Boolean --> defines if the LastMessage has been read or not.
    }

    fun setLastMessageAsRead(currentUserID: String, contactID: String) {
        lastMessagesColl.document(currentUserID).collection(LAST_MESSAGES_COLL).document(contactID)
            .set(mapOf(Pair(READ, true)), SetOptions.merge())
    }

    suspend fun addMessageAndLastMessage(
        message: Message,
        lastMessage: LastMessage,
        currentUsername: String,
        pushValue: String
    ) {
        fire.runBatch {
            addMessage(message, lastMessage.contactID, pushValue)
            addLastMessage(lastMessage, message.ownerID, currentUsername)
        }.await()
    }

    private fun addMessage(message: Message, receiverID: String, pushValue: String) {
        messagesColl.document(message.ownerID).collection(receiverID).document(pushValue)
            .set(message)

        messagesColl.document(receiverID).collection(message.ownerID).document(pushValue)
            .set(message)
    }

    private fun addLastMessage(
        lastMessage: LastMessage,
        currentUserID: String,
        currentUsername: String
    ) {
        val contactID = lastMessage.contactID

        lastMessagesColl.document(currentUserID).collection(LAST_MESSAGES_COLL)
            .document(contactID).set(lastMessage.apply { read = true })


        lastMessage.apply {
            read = false
            contactName = currentUsername
            this.contactID = currentUserID
        }

        lastMessagesColl.document(contactID).collection(LAST_MESSAGES_COLL)
            .document(currentUserID).set(lastMessage)
    }

}