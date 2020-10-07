package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.PreferencesManager
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class SignInViewModel(application: Application) : MyAndroidViewModel(application) {

    var email: String = getLastUserEmailPreference()
    var password: String = ""

    /** Sign in and return the username if the operation succeeds*/
    @Throws(MyException::class)
    suspend fun signInWithEmailAndPassword(): String? {
        verifySignInFieldsAreNotEmpty()
        try {
            addPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key, email)

            val authResult = AuthRepo.signIn(email, password)

            return getUsernameFromEmail(authResult.user?.email)

        } catch (e: FirebaseAuthInvalidUserException) {
            throw MyException(getString(R.string.userNotFoundOrDisabled))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw MyException(getString(R.string.wrongPassword))
        } catch (e: FirebaseTooManyRequestsException) {
            throw MyException(getString(R.string.tooManyFailedLoginAttempts))
        } catch (e: Exception) {
            e.printStackTrace()
            throw MyException(getString(R.string.undefinedError))
        }
    }

    @Throws(MyException::class)
    private fun verifySignInFieldsAreNotEmpty() {
        if (email.isBlank()) throw MyException(getString(R.string.provideEmail))
        if (password.isBlank()) throw MyException(getString(R.string.providePassword))
        if (!emailIsWellFormatted()) throw MyException(getString(R.string.emailBadlyFormatted))
    }

    private fun getUsernameFromEmail(fullEmail: String?) =
        fullEmail?.substringBefore("@") ?: getString(R.string.unknownUser)


    private fun emailIsWellFormatted() = email.contains(Regex("@. *"))

    private fun getLastUserEmailPreference() =
        PreferencesManager.getPreference(PreferencesManager.KEYS.LAST_USER_EMAIL.key) ?: ""


    private fun addPreference(key: String, value: String) {
        PreferencesManager.addPreference(key, value)
    }

}