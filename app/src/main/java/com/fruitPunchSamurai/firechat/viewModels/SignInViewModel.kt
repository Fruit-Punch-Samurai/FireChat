package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.models.User
import com.fruitPunchSamurai.firechat.others.EmailMatcher.emailIsWellFormatted
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel.getString
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.others.PreferencesManager
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    var state: MutableLiveData<MyState> = MutableLiveData(MyState.Idle)
    var email: String = getLastUserEmailPreference()
    var password: String = ""
    private val auth = AuthRepo()
    private val repo = MainRepo()

    private fun getUsernameFromEmail(fullEmail: String?) =
        fullEmail?.substringBefore("@") ?: getString(R.string.unknownUser)

    private fun getLastUserEmailPreference() =
        PreferencesManager.getPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key) ?: ""


    private fun addPreference(key: String, value: String) {
        PreferencesManager.addPreference(key, value)
    }

    fun setIdleState() {
        state.postValue(MyState.Idle)
    }


    private fun signInFieldsAreEmpty(): Boolean {
        if (email.isBlank()) {
            state.postValue(MyState.Error(getString(R.string.provideEmail)))
            return true
        }
        if (password.isBlank()) {
            state.postValue(MyState.Error(getString(R.string.providePassword)))
            return true
        }
        if (!emailIsWellFormatted(email)) {
            state.postValue(MyState.Error(getString(R.string.emailBadlyFormatted)))
            return true
        }
        return false
    }

    private suspend fun saveUsername() {
        if (auth.isLoggedIn()) {
            val authUsername = auth.getUsername()
            if (authUsername.isNullOrBlank()) auth.setUsername(getUsernameFromEmail(auth.getEmail()!!))

            val user = User(auth.getUID()!!, auth.getUsername()!!)
            if (repo.getUser(user.id)?.name.isNullOrBlank()) repo.addUser(user)
        }
    }


    /** Sign in and return the username if the operation succeeds*/
    suspend fun signInWithEmailAndPassword(): String? {
        state.postValue(MyState.Loading())
        if (signInFieldsAreEmpty()) return null

        return try {
            auth.signIn(email, password)
            addPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key, email)

            saveUsername()

            val username = getUsernameFromEmail(email)
            state.postValue(MyState.Finished("${getString(R.string.welcome)} $username"))
            username

        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(MyState.Error(e.message))
            null
        }
    }
}
