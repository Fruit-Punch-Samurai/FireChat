package com.fruitPunchSamurai.firechat.repos

import android.content.Context
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.github.rosariopfernandes.firecoil.FireCoil
import kotlinx.coroutines.tasks.await

class StorageRepo {

    private val storageRef = Firebase.storage.getReference("ChatMedia")

    suspend fun addChatImageToStorage(
        uri: Uri,
        pushValue: String,
        currentUserID: String,
        receiverID: String
    ) {
        storageRef.child(getPath(currentUserID, receiverID)).child("$pushValue.jpg").putFile(uri)
            .await()
    }

    suspend fun getImage(
        ctx: Context,
        mediaID: String,
        currentUserID: String,
        receiverID: String
    ) = FireCoil.get(
        ctx, storageRef.child(getPath(currentUserID, receiverID)).child(mediaID)
    ).drawable?.toBitmap()


    /* Sorts the users IDs alphabetically to create the path in which the media will be stored */
    private fun getPath(currentUserID: String, receiverID: String): String {
        var path = ""

        ArrayList<String>().apply {
            add(currentUserID)
            add(receiverID)
            sort()
        }.forEach { path = "$path$it" }

        return path
    }

}