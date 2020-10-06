package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.others.MyAndroidViewModel
import com.fruitPunchSamurai.firechat.others.MyException

class SignUpViewModel(application: Application) : MyAndroidViewModel(application) {

    var email: MutableLiveData<String> = MutableLiveData()
    var usernameSentence: MutableLiveData<String> = MutableLiveData()
    var password = ""
    var confirmedPassword = ""

    @Throws(MyException::class)
    private fun verifySignInFieldsAreNotEmpty() {
        if (email.value.isNullOrBlank()) throw MyException(getString(R.string.provideEmail))
        if (password.isBlank()) throw MyException(getString(R.string.providePassword))
        if (confirmedPassword.isBlank()) throw MyException(getString(R.string.pleaseConfirmPassword))
        if (!emailIsWellFormatted()) throw MyException(getString(R.string.emailBadlyFormatted))
    }

    fun getUsernameFromEmail() =
        if (emailIsWellFormatted()) email.value.toString().substringBefore("@") else ""


    private fun emailIsWellFormatted() = email.value.toString().contains(Regex("@. *"))
}