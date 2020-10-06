package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.repos.AuthRepo

class HomeViewModel(application: Application) : MyAndroidViewModel(application) {

    fun userIsLoggedIn() = AuthRepo.isLoggedIn()

}