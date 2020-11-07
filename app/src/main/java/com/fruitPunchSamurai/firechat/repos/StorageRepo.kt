package com.fruitPunchSamurai.firechat.repos

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class StorageRepo {

    private val storageRef = Firebase.storage.getReference("ChatMedia")

    suspend fun addChatImageToStorage(
        uri: Uri,
        pushValue: String,
        currentUserID: String,
        receiverID: String
    ) {
        storageRef.child(currentUserID).child(receiverID).child("$pushValue.pg").putFile(uri)
            .await()
        storageRef.child(receiverID).child(currentUserID).child("$pushValue.pg").putFile(uri)
            .await()
    }
}