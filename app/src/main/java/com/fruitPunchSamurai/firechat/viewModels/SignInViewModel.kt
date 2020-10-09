package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.others.PreferencesManager
import com.fruitPunchSamurai.firechat.repos.AuthRepo

class SignInViewModel(application: Application) : MyAndroidViewModel(application) {

    var state: MutableLiveData<MyState> = MutableLiveData(MyState.Idle)
    var email: String = getLastUserEmailPreference()
    var password: String = ""
    private val auth = AuthRepo()

    /** Sign in and return the username if the operation succeeds*/
    suspend fun signInWithEmailAndPassword(): String? {
        state.value = MyState.Loading
        if (signInFieldsAreEmpty()) return null
        return try {
            val authResult = auth.signIn(email, password)
            addPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key, email)
            val username = getUsernameFromEmail(authResult.user?.email)
            state.value = MyState.Finished("${getString(R.string.welcome)} $username")
            username

        } catch (e: Exception) {
            e.printStackTrace()
            val ex = MyException(e.message)
            state.value = MyState.Error(ex.message)
            null
        }
    }

    private fun signInFieldsAreEmpty(): Boolean {
        if (email.isBlank()) {
            state.value = MyState.Error(getString(R.string.provideEmail))
            return true
        }
        if (password.isBlank()) {
            state.value = MyState.Error(getString(R.string.providePassword))
            return true
        }
        if (!emailIsWellFormatted()) {
            state.value = MyState.Error(getString(R.string.emailBadlyFormatted))
            return true
        }
        return false
    }

    private fun getUsernameFromEmail(fullEmail: String?) =
        fullEmail?.substringBefore("@") ?: getString(R.string.unknownUser)


    private fun emailIsWellFormatted() = email.contains(Regex("@. *"))

    private fun getLastUserEmailPreference() =
        PreferencesManager.getPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key) ?: ""


    private fun addPreference(key: String, value: String) {
        PreferencesManager.addPreference(key, value)
    }

    fun setIdleState() {
        state.value = MyState.Idle
    }

}