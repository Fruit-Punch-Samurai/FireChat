package com.fruitPunchSamurai.firechat.viewModels

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.PreferencesManager
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.google.firebase.auth.AuthResult

class SignInViewModel : ViewModel() {

    suspend fun signInWithEmailAndPassword(
        appContext: Context,
        email: String,
        password: String
    ): AuthResult? {
        if (AuthRepo.isLoggedIn()) AuthRepo.logOut()
        verifySignInFieldsAreNotEmpty(appContext, email, password)
        addPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key, email)
        return AuthRepo.signIn(email, password)
    }

    @Throws(MyException::class)
    private fun verifySignInFieldsAreNotEmpty(
        appContext: Context,
        email: String,
        password: String
    ) {
        if (TextUtils.isEmpty(email)) throw MyException(appContext.getString(R.string.provideEmail))
        if (TextUtils.isEmpty(password)) throw MyException(appContext.getString(R.string.providePassword))
        if (!emailIsWellFormatted(email)) throw MyException(appContext.getString(R.string.emailBadlyFormatted))
    }

    private fun emailIsWellFormatted(email: String): Boolean = email.contains(Regex("@. *"))

    fun getLastUserEmailPreference(): String? =
        PreferencesManager.getPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key)

    private fun addPreference(key: String, value: String) {
        PreferencesManager.addPreference(key, value)
    }

}