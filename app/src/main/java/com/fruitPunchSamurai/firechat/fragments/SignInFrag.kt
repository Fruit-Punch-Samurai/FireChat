package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignInFragmentBinding
import com.fruitPunchSamurai.firechat.viewModels.SignInViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import others.MyException

class SignInFrag : Fragment() {

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
        val email = signInUsername.text.toString()
        val password = signInPassword.text.toString()
        GlobalScope.launch {
            try {
                val result = viewModel.signInWithEmailAndPassword(requireContext(), email, password)
                if (result != null) Snackbar.make(
                    requireView(),
                    getString(R.string.welcome) + " ${result.user!!.email}",
                    2000
                ).show()

            } catch (e: MyException) {
                e.printStackTrace()
                Snackbar.make(requireView(), e.localizedMessage, 2000).show()
            } catch (e: FirebaseAuthInvalidUserException) {
                Snackbar.make(requireView(), getString(R.string.userNotFound), 2000).show()
            } catch (e: FirebaseAuthInvalidCredentialsException) {

                if (!email.contains("@")) Snackbar.make(
                    requireView(),
                    getString(R.string.emailBadlyFormatted),
                    2000
                ).show()
                else Snackbar.make(requireView(), getString(R.string.wrongPassword), 2000).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(requireView(), "Undefined Exception", 2000).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}