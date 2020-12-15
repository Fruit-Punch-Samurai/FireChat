package com.fruitPunchSamurai.firechat.viewModels

import androidx.lifecycle.ViewModel
import com.fruitPunchSamurai.firechat.repos.MainRepo

class FullImageViewModel : ViewModel() {

    private val repo = MainRepo()

    suspend fun getImageURI(imageID: String, receiverID: String) =
        repo.getImageURI(imageID, receiverID)

}