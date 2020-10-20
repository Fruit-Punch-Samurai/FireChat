package com.fruitPunchSamurai.firechat.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class ChatViewModel : ViewModel() {

    val newMessage = MutableLiveData<String>()
    val state = MutableLiveData<MyState>(MyState.Idle)
    private val auth = AuthRepo()
    private val repo = MainRepo()

    fun getCurrentUserID() = auth.getUID()!!

    suspend fun sendMessage(receiverID: String) {
        if (newMessage.value.isNullOrBlank()) return

        state.postValue(MyState.Loading())

        try {
            val message = Message()
            val lastMessage = LastMessage()

            message.apply {
                date = DateTime.now(DateTimeZone.UTC).toString()
                msg = newMessage.value!!
                ownerID = auth.getUID()!!
            }

            lastMessage.apply {
                msg = newMessage.value!!
                contactID = receiverID
                read = false
                //TODO: Fix name
                contactName = "Test"
            }

            repo.addMessage(message, auth.getUID()!!, receiverID)
            repo.addLastMessage(lastMessage, auth.getUID()!!, receiverID)
            state.postValue(MyState.Finished())
            newMessage.postValue("")
        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(MyState.Error(e.localizedMessage))
        }
    }
}