package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.models.Message
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FireMessagesRepo {

    private enum class FIELDS(val field: String) {
        MESSAGE("msg"),
        OWNER("ownerID"),
        DATE("date"),
        TIMESTAMP("tms"),
        READ("read")
    }

    private val fire = Firebase.firestore
    private val rdb = Firebase.database
    private val messagesColl = fire.collection("Messages")
    private val lastMessagesColl = fire.collection("LastMessages")

    fun setLastMessageAsRead(currentUserID: String, contactID: String) {
        val map = HashMap<String, Any>()
        map["read"] = true

        lastMessagesColl.document(currentUserID).collection("LastMessages").document(contactID)
            .set(map, SetOptions.merge())
    }

    suspend fun addMessageAndLastMessage(
        message: Message,
        lastMessage: LastMessage,
        receiverID: String,
        currentUsername: String
    ) {
        fire.runBatch {
            addMessage(message, receiverID)
            addLastMessage(lastMessage, message.ownerID, currentUsername)
        }.await()
    }

    private fun addMessage(message: Message, receiverID: String) {
        val pushValue = rdb.getReference("Messages").push().key ?: return
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
        lastMessagesColl.document(currentUserID).collection("LastMessages")
            .document(lastMessage.contactID).set(lastMessage.apply { read = true })
        lastMessagesColl.document(lastMessage.contactID).collection("LastMessages")
            .document(currentUserID)
            .set(lastMessage.apply {
                read = false;contactName = currentUsername;contactID = currentUserID
            })
    }

}