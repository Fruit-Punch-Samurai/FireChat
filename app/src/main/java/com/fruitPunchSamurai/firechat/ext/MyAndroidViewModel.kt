package com.fruitPunchSamurai.firechat.ext

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

object MyAndroidViewModel {

    private fun AndroidViewModel.getApplicationContext(): Context =
        getApplication<Application>().applicationContext

    fun AndroidViewModel.getString(resID: Int): String = getApplicationContext().getString(resID)

    fun AndroidViewModel.getExternalFilesDir(type: String?) =
        getApplicationContext().getExternalFilesDir(type)
}