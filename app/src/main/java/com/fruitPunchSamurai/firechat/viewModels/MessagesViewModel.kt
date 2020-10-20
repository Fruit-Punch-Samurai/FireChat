package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fruitPunchSamurai.firechat.repos.AuthRepo

class MessagesViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = AuthRepo()

    fun getCurrentUserID() = auth.getUID()

}