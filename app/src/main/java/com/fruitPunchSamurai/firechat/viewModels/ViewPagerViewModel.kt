package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel.getString
import com.fruitPunchSamurai.firechat.repos.AuthRepo

class ViewPagerViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = AuthRepo()
    val usernameText: MutableLiveData<String> =
        MutableLiveData("${getString(R.string.signedInAs)} ${auth.getUsername()}")

    fun logout() {
        auth.logOut()
    }


}