package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.models.User
import com.fruitPunchSamurai.firechat.others.EmailMatcher.emailIsWellFormatted
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel.getString
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo
import com.google.firebase.auth.AuthResult
import java.util.*

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val fire = MainRepo()
    private val auth = AuthRepo()
    var state: MutableLiveData<MyState> = MutableLiveData(MyState.Idle)
    var email = MutableLiveData("null")
    var password = ""
    var confirmedPassword = ""

    fun getUsernameFromEmail() =
        if (emailIsWellFormatted(email.value.toString())) email.value.toString()
            .substringBefore("@")
            .capitalize(Locale.getDefault()) else ""

    fun setIdleState() {
        state.postValue(MyState.Idle)
    }

    private fun passwordsAreIdentical(): Boolean {
        return if (password != confirmedPassword) {
            state.postValue(MyState.Error(getString(R.string.pleaseConfirmPassword)))
            false
        } else true
    }

    private suspend fun saveUsername(authResult: AuthResult) {
        saveUsernameToAuth()
        saveUsernameToFirestore(authResult)
    }

    private suspend fun saveUsernameToAuth() {
        auth.setUsername(getUsernameFromEmail())
    }

    private suspend fun saveUsernameToFirestore(authResult: AuthResult) {
        val user = User(authResult.user?.uid.toString(), getUsernameFromEmail())
        fire.addUser(user)
    }


    private fun signInFieldsAreEmpty(): Boolean {
        if (email.value.isNullOrBlank()) {
            state.postValue(MyState.Error(getString(R.string.provideEmail)))
            return true
        }
        if (password.isBlank()) {
            state.postValue(MyState.Error(getString(R.string.providePassword)))
            return true
        }
        if (confirmedPassword.isBlank()) {
            state.postValue(MyState.Error(getString(R.string.pleaseConfirmPassword)))
            return true
        }
        if (!emailIsWellFormatted(email.value.toString())) {
            state.postValue(MyState.Error(getString(R.string.emailBadlyFormatted)))
            return true
        }
        return false
    }

    /** Sign up and return the username if the operation succeeds*/
    @Throws(MyException::class)
    suspend fun signUp(): String? {
        state.postValue(MyState.Loading())

        if (signInFieldsAreEmpty() || !passwordsAreIdentical()) return null

        return try {
            val authResult = auth.signUp(email.value!!, password)
            saveUsername(authResult)

            val username = getUsernameFromEmail()
            state.postValue(MyState.Finished("${getString(R.string.welcome)} $username"))
            username
        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(MyState.Error(e.message))
            null
        }
    }
}