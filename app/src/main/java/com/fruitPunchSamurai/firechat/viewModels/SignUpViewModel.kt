package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.util.*

class SignUpViewModel(application: Application) : MyAndroidViewModel(application) {

    var email: MutableLiveData<String> = MutableLiveData()
    var usernameSentence: MutableLiveData<String> = MutableLiveData()
    var password = ""
    var confirmedPassword = ""

    fun getUsernameFromEmail() =
        if (emailIsWellFormatted()) email.value.toString().substringBefore("@")
            .capitalize(Locale.getDefault()) else ""

    private fun emailIsWellFormatted() = email.value.toString().contains(Regex("@. *"))


    @Throws(MyException::class)
    private fun verifySignInFieldsAreNotEmpty() {
        if (email.value.isNullOrBlank()) throw MyException(getString(R.string.provideEmail))
        if (password.isBlank()) throw MyException(getString(R.string.providePassword))
        if (confirmedPassword.isBlank()) throw MyException(getString(R.string.pleaseConfirmPassword))
        if (!emailIsWellFormatted()) throw MyException(getString(R.string.emailBadlyFormatted))
    }

    private fun verifyPasswordsAreIdentical() {
        if (password != confirmedPassword) throw MyException(getString(R.string.pleaseConfirmPassword))
    }

    /** Sign up and return the username if the operation succeeds*/
    @Throws(MyException::class)
    suspend fun signUp(): String {
        verifySignInFieldsAreNotEmpty()
        verifyPasswordsAreIdentical()
        try {
            AuthRepo.signUp(email.value.toString(), password)
            setUsername(getUsernameFromEmail())
            return getUsernameFromEmail()
        } catch (e: FirebaseAuthWeakPasswordException) {
            throw MyException(getString(R.string.chooseStrongerPassword))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw MyException(getString(R.string.invalidCredentials))
        } catch (e: FirebaseAuthUserCollisionException) {
            throw MyException(getString(R.string.emailAddressTaken))
        } catch (e: FirebaseNetworkException) {
            throw MyException(getString(R.string.noNetworkConnection))
        } catch (e: Exception) {
            e.printStackTrace()
            throw MyException(getString(R.string.undefinedError))
        }
    }

    @Throws(FirebaseAuthInvalidUserException::class)
    suspend fun setUsername(username: String) {
        AuthRepo.setUsername(username)
    }
}