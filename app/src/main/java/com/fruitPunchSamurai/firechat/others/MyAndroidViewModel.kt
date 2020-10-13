package com.fruitPunchSamurai.firechat.others

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

object MyAndroidViewModel {

    fun AndroidViewModel.getApplicationContext(): Context =
        getApplication<Application>().applicationContext

    fun AndroidViewModel.getString(resID: Int): String = getApplicationContext().getString(resID)
}