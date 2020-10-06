package com.fruitPunchSamurai.firechat.fragments

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignInFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.SignInViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignInFrag : MyFrag() {

    private var b: SignInFragmentBinding? = null
    private val vm: SignInViewModel by viewModels()

    override fun initiateDataBinder(container: ViewGroup?): View? {
        b = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.sign_in_fragment,
            container,
            false
        ) as SignInFragmentBinding
//        b = SignInFragmentBinding.inflate(layoutInflater, container, false)
        b?.lifecycleOwner = viewLifecycleOwner
        b?.viewModel = vm
        return b?.root
    }

    override fun bindData() {
        b?.viewModel = vm
        b?.frag = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    private fun welcomeUser(email: String) {
        showSnackBar(R.string.welcome, " $email")
    }

    private fun goToViewPagerFrag() {
        navigateTo(R.id.action_signInFrag_to_viewPagerFrag)
    }

    fun signInWithEmailAndPassword() {
        hideKeyboard()
        makeLayoutTouchable(false)

        MainScope().launch {
            try {
                val username = vm.signInWithEmailAndPassword()
                if (username != null) {
                    welcomeUser(username)
                    goToViewPagerFrag()
                }

            } catch (e: MyException) {
                showSnackBar(e.localizedMessage)
            }
            makeLayoutTouchable(true)

        }
    }
}