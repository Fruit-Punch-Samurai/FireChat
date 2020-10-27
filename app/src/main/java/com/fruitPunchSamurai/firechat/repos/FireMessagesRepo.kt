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

    suspend fun addMessage(message: Message, currentUserID: String, receiverID: String) {
        val pushValue = rdb.getReference("Messages").push().key ?: return
        messagesColl.document(currentUserID).collection(receiverID).document(pushValue).set(message)
            .await()
        messagesColl.document(receiverID).collection(currentUserID).document(pushValue).set(message)
            .await()
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
            .set(lastMessage.apply {
                read = false;contactName = currentUsername;contactID = currentUserID
            }).await()
    }

}