package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignInFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.SignInViewModel
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignInFrag : MyFrag() {

    private var b: SignInFragmentBinding? = null

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        b = SignInFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(SignInViewModel::class.java)

        b?.viewModel = viewModel
        b?.frag = this
    }

    fun signInWithEmailAndPassword() {
        hideKeyboard()
        makeLayoutTouchable(false)

        val email = signInEmail.text.toString()
        val password = signInPassword.text.toString()

        MainScope().launch {
            try {
                val authResult =
                    viewModel.signInWithEmailAndPassword(email, password)
                if (authResult != null) {
                    welcomeUser(authResult)
                    goToViewPagerFrag()
                }

            } catch (e: MyException) {
                showSnackBar(e.localizedMessage)
            } catch (e: FirebaseAuthInvalidUserException) {
                showSnackBar(R.string.userNotFound)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                showSnackBar(R.string.wrongPassword)
            } catch (e: FirebaseTooManyRequestsException) {
                showSnackBar(R.string.tooManyFailedLoginAttempts)
            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBar(R.string.undefinedError)
            }
            makeLayoutTouchable(true)

        }
    }

    private fun welcomeUser(result: AuthResult) {
        showSnackBar(R.string.welcome, " ${result.user!!.email}")
    }

    private fun goToViewPagerFrag() {
        navigateTo(R.id.action_signInFrag_to_viewPagerFrag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}