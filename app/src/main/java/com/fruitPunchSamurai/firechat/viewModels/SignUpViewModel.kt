package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.models.User
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo
import com.google.firebase.auth.AuthResult
import java.util.*

class SignUpViewModel(application: Application) : MyAndroidViewModel(application) {

    private val fire = MainRepo()
    private val auth = AuthRepo()
    var state: MutableLiveData<MyState> = MutableLiveData(MyState.Idle)
    var email = MutableLiveData<String>()
    var password = ""
    var confirmedPassword = ""

    fun getUsernameFromEmail() =
        if (emailIsWellFormatted()) email.value.toString().substringBefore("@")
            .capitalize(Locale.getDefault()) else ""

    private fun emailIsWellFormatted() = email.value!!.matches(Regex(".+@\\w+[.]\\w+.*"))

    fun setIdleState() {
        state.value = MyState.Idle
    }

    private fun passwordsAreIdentical(): Boolean {
        return if (password != confirmedPassword) {
            state.value = MyState.Error(getString(R.string.pleaseConfirmPassword))
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
            state.value = MyState.Error(getString(R.string.provideEmail))
            return true
        }
        if (password.isBlank()) {
            state.value = MyState.Error(getString(R.string.providePassword))
            return true
        }
        if (confirmedPassword.isBlank()) {
            state.value = MyState.Error(getString(R.string.pleaseConfirmPassword))
            return true
        }
        if (!emailIsWellFormatted()) {
            state.value = MyState.Error(getString(R.string.emailBadlyFormatted))
            return true
        }
        return false
    }

    /** Sign up and return the username if the operation succeeds*/
    @Throws(MyException::class)
    suspend fun signUp(): String? {
        state.value = MyState.Loading

        if (signInFieldsAreEmpty() || !passwordsAreIdentical()) return null

        return try {
            val authResult = auth.signUp(email.value!!, password)
            saveUsername(authResult)

            val username = getUsernameFromEmail()
            state.value = MyState.Finished("${getString(R.string.welcome)} $username")
            username
        } catch (e: Exception) {
            e.printStackTrace()
            state.value = MyState.Error(e.message!!)
            null
        }
    }
}