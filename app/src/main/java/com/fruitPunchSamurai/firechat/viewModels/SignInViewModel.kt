package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.PreferencesManager
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.google.firebase.auth.AuthResult

class SignInViewModel(application: Application) : MyAndroidViewModel(application) {

    var email: String = ""
    var password: String = ""

    suspend fun signInWithEmailAndPassword(): AuthResult? {
        println(email)
        println(password)
        if (AuthRepo.isLoggedIn()) AuthRepo.logOut()

        verifySignInFieldsAreNotEmpty()
        addPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key, email)
        return AuthRepo.signIn(email, password)
    }

    @Throws(MyException::class)
    private fun verifySignInFieldsAreNotEmpty() {
        if (email.isBlank()) throw MyException(getString(R.string.provideEmail))
        if (password.isBlank()) throw MyException(getString(R.string.providePassword))
        if (!emailIsWellFormatted()) throw MyException(getString(R.string.emailBadlyFormatted))
    }

    private fun emailIsWellFormatted(): Boolean = email.contains(Regex("@. *"))

    fun getLastUserEmailPreference() {
        email = PreferencesManager.getPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key) ?: ""
    }

    private fun addPreference(key: String, value: String) {
        PreferencesManager.addPreference(key, value)
    }

}