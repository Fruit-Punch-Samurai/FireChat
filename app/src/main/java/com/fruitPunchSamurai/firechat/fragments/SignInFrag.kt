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

        viewModel.getLastUserEmailPreference()

        b?.viewModel = viewModel
        b?.frag = this

    }

    fun signInWithEmailAndPassword() {
        hideKeyboard()
        makeLayoutTouchable(false)

        MainScope().launch {
            try {
                val result = viewModel.signInWithEmailAndPassword()
                if (result != null) {
                    welcomeUser(result)
                    goToViewPagerFrag()
                }

            } catch (e: MyException) {
                showSnackBar(e.localizedMessage)
            }
            makeLayoutTouchable(true)

        }
    }

    private fun welcomeUser(email: String) {
        showSnackBar(R.string.welcome, " $email")
    }

    private fun goToViewPagerFrag() {
        navigateTo(R.id.action_signInFrag_to_viewPagerFrag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}