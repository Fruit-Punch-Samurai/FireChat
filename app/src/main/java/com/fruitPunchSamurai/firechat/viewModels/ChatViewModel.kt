package com.fruitPunchSamurai.firechat.viewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fruitPunchSamurai.firechat.adapters.ChatRecyclerAdapter
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo
import org.joda.time.DateTime

class ChatViewModel : ViewModel() {

    val newMessage = MutableLiveData<String>()
    val state = MutableLiveData<MyState>(MyState.Idle)
    private val auth = AuthRepo()
    private val repo = MainRepo()
    lateinit var adapter: ChatRecyclerAdapter

    fun initiateTheRecyclerAdapter(lifecycleOwner: LifecycleOwner) {
        if (!::adapter.isInitialized) adapter =
            ChatRecyclerAdapter(RecyclerOptions.getMessagesOption(lifecycleOwner, auth.getUID()!!))
    }


    suspend fun sendMessage(receiverID: String) {
        if (newMessage.value.isNullOrBlank()) return

        state.postValue(MyState.Loading())

        try {
            val message = Message()
            message.apply {
                date = DateTime().toDateTime()
                msg = newMessage.value!!
                ownerID = auth.getUID()!!
            }
            repo.addMessage(message, auth.getUID()!!, receiverID)
            state.postValue(MyState.Finished())
        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(MyState.Error(e.localizedMessage))
        }
    }
}