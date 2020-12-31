package com.fruitPunchSamurai.firechat.others

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.models.User
import com.fruitPunchSamurai.firechat.repos.FireMessagesRepo
import com.fruitPunchSamurai.firechat.repos.FireUsersRepo
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object RecyclerOptions {

    private val rootCollection = Firebase.firestore

    fun getAllUsersPagingOption(lifecycleOwner: LifecycleOwner): FirestorePagingOptions<User> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .setPageSize(20)
            .build()
        return FirestorePagingOptions.Builder<User>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(
                rootCollection.collection(FireUsersRepo.USERS_COLL).orderBy(User.NAME),
                config,
                User::class.java
            ).build()
    }

    fun getMessagesOption(
        lifecycleOwner: LifecycleOwner,
        userID: String,
        receiverID: String
    ): FirestoreRecyclerOptions<Message> =
        FirestoreRecyclerOptions.Builder<Message>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(
                rootCollection.collection(FireMessagesRepo.MESSAGES_COLL)
                    .document(getPath(userID, receiverID))
                    .collection(FireMessagesRepo.MESSAGES_COLL)
                    .orderBy(Message.TIMESTAMP, Query.Direction.ASCENDING), Message::class.java
            ).build()


    fun getLastMessagesOption(
        lifecycleOwner: LifecycleOwner,
        userID: String,
    ): FirestoreRecyclerOptions<LastMessage> =
        FirestoreRecyclerOptions.Builder<LastMessage>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(
                rootCollection.collection(FireMessagesRepo.LAST_MESSAGES_COLL)
                    .document(userID).collection(FireMessagesRepo.LAST_MESSAGES_COLL)
                    .orderBy(LastMessage.TIMESTAMP, Query.Direction.DESCENDING),
                LastMessage::class.java
            ).build()


    /* Sorts the users IDs alphabetically to create the path in which the Message will be stored */
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