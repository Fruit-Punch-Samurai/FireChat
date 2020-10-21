package com.fruitPunchSamurai.firechat.repos

import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.models.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FireMessagesRepo {

    private enum class FIELDS(val field: String) {
        MESSAGE("msg"),
        OWNER("ownerID"),
        DATE("date"),
        TIMESTAMP("tms")
    }

    private val fire = Firebase.firestore
    private val messagesColl = fire.collection("Messages")
    private val lastMessagesColl = fire.collection("LastMessages")

    suspend fun addMessage(message: Message, currentUserID: String, receiverID: String) {
        messagesColl.document(currentUserID).collection(receiverID).document().set(message).await()
        messagesColl.document(receiverID).collection(currentUserID).document().set(message).await()
    }

    suspend fun addLastMessage(
        lastMessage: LastMessage,
        currentUserID: String,
        receiverID: String,
        currentUsername: String
    ) {
        lastMessagesColl.document(currentUserID).collection("LastMessages")
            .document(receiverID).set(lastMessage.apply { read = true }).await()
        lastMessagesColl.document(receiverID).collection("LastMessages")
            .document(currentUserID)
            .set(lastMessage.apply { read = false;contactName = currentUsername }).await()
    }

}