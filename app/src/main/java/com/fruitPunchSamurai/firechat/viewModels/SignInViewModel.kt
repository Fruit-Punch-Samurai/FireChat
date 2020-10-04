package com.fruitPunchSamurai.firechat.viewModels

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.fruitPunchSamurai.firechat.R
import com.google.firebase.auth.AuthResult
import models.CurrentUser
import others.MyException

class SignInViewModel : ViewModel() {

    @Throws(Exception::class)
    suspend fun signInWithEmailAndPassword(
        appContext: Context,
        email: String,
        password: String
    ): AuthResult? {
        if (CurrentUser.isLoggedIn()) CurrentUser.logOut()
        verifySignInFieldsAreNotEmpty(appContext, email, password)
        return CurrentUser.signIn(email, password)
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

}