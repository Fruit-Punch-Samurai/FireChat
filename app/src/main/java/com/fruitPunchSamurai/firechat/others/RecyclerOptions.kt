package com.fruitPunchSamurai.firechat.others

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.FirebaseFirestore

object RecyclerOptions {

    private val rootCollection = FirebaseFirestore.getInstance()

    fun getAllUsersPagingOption(lifecycleOwner: LifecycleOwner): FirestorePagingOptions<User> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .setPageSize(20)
            .build()
        return FirestorePagingOptions.Builder<User>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(rootCollection.collection("Users").orderBy("name"), config, User::class.java)
            .build()

    }

    fun getMessagesOption(
        lifecycleOwner: LifecycleOwner,
        userID: String,
        receiverID: String
    ): FirestoreRecyclerOptions<Message> {
        return FirestoreRecyclerOptions.Builder<Message>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(
                rootCollection.collection("Messages")
                    .document(userID)
                    .collection(receiverID).orderBy("tms"),
                Message::class.java
            )
            .build()

    }
}