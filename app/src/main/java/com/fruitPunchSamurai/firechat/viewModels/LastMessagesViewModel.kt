package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo

class LastMessagesViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = AuthRepo()
    private val repo = MainRepo()

    fun setMessageAsRead(lastMessage: LastMessage) {
        repo.setLastMessageAsRead(auth.getUID()!!, lastMessage.contactID)
    }

    fun getCurrentUserID() = auth.getUID()

}