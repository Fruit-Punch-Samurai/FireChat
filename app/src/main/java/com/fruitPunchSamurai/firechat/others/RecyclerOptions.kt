package com.fruitPunchSamurai.firechat.others

import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.fruitPunchSamurai.firechat.models.User
import com.google.firebase.firestore.FirebaseFirestore

object RecyclerOptions {

    private val rootCollection = FirebaseFirestore.getInstance()

    fun getAllUsersOption(lifecycleOwner: LifecycleOwner): FirestoreRecyclerOptions<User> {
        return FirestoreRecyclerOptions.Builder<User>()
            .setQuery(rootCollection.collection("Users"), User::class.java)
            .setLifecycleOwner(lifecycleOwner)
            .build()

    }
}