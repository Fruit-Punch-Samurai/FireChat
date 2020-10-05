package com.fruitPunchSamurai.firechat.others

import android.content.Context
import android.content.SharedPreferences
import com.fruitPunchSamurai.firechat.R

object PreferencesManager {

    enum class KEYS(val key: String) {
        LAST_USER_EMAIL("lastUserEmail")
    }

    private lateinit var context: Context

    /**Gets the context of the app, Must be called at app start*/
    fun initialize(context: Context) {
        this.context = context
        getPreference("lastUserEmail")
    }

    private fun getAllPreferences(): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.preference_file_name), Context.MODE_PRIVATE
        )
    }

    fun getPreference(key: String): String? {
        return getAllPreferences().getString(key, null)
    }

    fun addPreference(key: String, value: String) {
        getAllPreferences().edit().putString(key, value).apply()
    }

}