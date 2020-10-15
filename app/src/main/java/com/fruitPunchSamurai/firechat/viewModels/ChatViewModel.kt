package com.fruitPunchSamurai.firechat.viewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.fruitPunchSamurai.firechat.adapters.ChatRecyclerAdapter
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.repos.AuthRepo

class ChatViewModel : ViewModel() {

    private val auth = AuthRepo()
    lateinit var adapter: ChatRecyclerAdapter

    fun initiateTheRecyclerAdapter(lifecycleOwner: LifecycleOwner) {
        if (!::adapter.isInitialized) adapter =
            ChatRecyclerAdapter(RecyclerOptions.getMessagesOption(lifecycleOwner, auth.getUID()!!))
    }
}