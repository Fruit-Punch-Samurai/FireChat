package com.fruitPunchSamurai.firechat.others

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

open class MyAndroidViewModel(app: Application) : AndroidViewModel(app) {

    fun getApplicationContext(): Context = getApplication<Application>().applicationContext

    fun getString(resID: Int): String = getApplicationContext().getString(resID)
}