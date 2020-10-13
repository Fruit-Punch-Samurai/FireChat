package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = AuthRepo()
    private val repo = MainRepo()

    fun userIsLoggedIn() = auth.isLoggedIn()

    fun logout() = auth.logOut()

    suspend fun userExistsInDatabase() = !repo.getUser(auth.getUID())?.name.isNullOrBlank()

}