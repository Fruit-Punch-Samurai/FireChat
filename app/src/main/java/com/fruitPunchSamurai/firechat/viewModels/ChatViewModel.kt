package com.fruitPunchSamurai.firechat.viewModels

import android.net.Uri
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

    fun setIdleState() = state.postValue(MyState.Idle)

    suspend fun sendTextMessage(receiverID: String, receiverName: String) {
        if (newMessage.value.isNullOrBlank()) return

        state.postValue(MyState.Loading())

        try {
            val message = createTextMessage()
            val lastMessage = createLastMessage(receiverID, receiverName)

            newMessage.postValue("")
            repo.addTextMessageAndLastMessage(message, lastMessage)

            state.postValue(MyState.Finished())
        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(MyState.Error(e.localizedMessage))
        }
    }

    suspend fun sendImageMessage(receiverID: String, receiverName: String, uri: Uri) {
        state.postValue(MyState.Loading())

        try {
            val message = createImageMessage()
            val lastMessage = createLastMessage(receiverID, receiverName)

            repo.addImageMessageAndLastMessage(message, lastMessage, uri)

            state.postValue(MyState.Finished())
        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(MyState.Error(e.localizedMessage))
        }
    }

    private fun createTextMessage() = Message().apply {
        date = DateTime.now(DateTimeZone.UTC).toString()
        msg = newMessage.value!!
        ownerID = auth.getUID()!!
        type = Message.TEXT
    }

    private fun createImageMessage() = Message().apply {
        date = DateTime.now(DateTimeZone.UTC).toString()
        ownerID = auth.getUID()!!
        type = Message.IMAGE
    }

    private fun createLastMessage(receiverID: String, receiverName: String) = LastMessage().apply {
        msg = newMessage.value.toString()
        contactID = receiverID
        read = false
        contactName = receiverName
    }


}
