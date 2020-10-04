package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignInFragmentBinding
import com.fruitPunchSamurai.firechat.viewModels.SignInViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import others.MyException

class SignInFrag : MyFrag() {

    //TODO: Remember name of last user

    private var b: SignInFragmentBinding? = null

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = SignInFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        signInBtn.setOnClickListener { signInWithEmailAndPassword() }
    }

    private fun signInWithEmailAndPassword() {
        hideKeyboard()
        val email = signInUsername.text.toString()
        val password = signInPassword.text.toString()

        GlobalScope.launch {
            try {
                val result = viewModel.signInWithEmailAndPassword(requireContext(), email, password)
                if (result != null) {
                    welcomeUser(result)
                    goToViewPagerFrag()
                }

            } catch (e: MyException) {
                showSnackBar(e.localizedMessage)
            } catch (e: FirebaseAuthInvalidUserException) {
                showSnackBar(R.string.userNotFound)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                showSnackBar(R.string.wrongPassword)
            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBar("Undefined Exception")
            }
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