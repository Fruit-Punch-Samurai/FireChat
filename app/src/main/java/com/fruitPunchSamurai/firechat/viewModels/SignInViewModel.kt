package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.PreferencesManager
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.google.firebase.auth.AuthResult

class SignInViewModel(application: Application) : MyAndroidViewModel(application) {

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult? {
        if (AuthRepo.isLoggedIn()) AuthRepo.logOut()

        verifySignInFieldsAreNotEmpty(email, password)
        addPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key, email)
        return AuthRepo.signIn(email, password)
    }

    @Throws(MyException::class)
    private fun verifySignInFieldsAreNotEmpty(email: String, password: String) {
        if (email.isBlank()) throw MyException(getString(R.string.provideEmail))
        if (password.isBlank()) throw MyException(getString(R.string.providePassword))
        if (!emailIsWellFormatted(email)) throw MyException(getString(R.string.emailBadlyFormatted))
    }

    private fun emailIsWellFormatted(email: String): Boolean = email.contains(Regex("@. *"))

    fun getLastUserEmailPreference(): String? =
        PreferencesManager.getPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key)

    private fun addPreference(key: String, value: String) {
        PreferencesManager.addPreference(key, value)
    }

}