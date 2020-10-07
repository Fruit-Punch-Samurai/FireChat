package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignUpFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyException
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.SignUpViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignUpFrag : MyFrag() {

    private val vm: SignUpViewModel by viewModels()
    private var b: SignUpFragmentBinding? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeEmailChanges()
    }

    override fun initiateDataBinder(container: ViewGroup?): View? {
        b = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.sign_up_fragment,
            container,
            false
        ) as SignUpFragmentBinding
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

    private fun observeEmailChanges() {
        vm.email.observe(viewLifecycleOwner, {
            val username = vm.getUsernameFromEmail()
            val sentence = getString(R.string.yourUsernameWillBe)
            vm.usernameSentence.value = "$sentence $username"
        })
    }

    fun signUp() {
        hideKeyboard()
        makeLayoutTouchable(false)
        MainScope().launch {
            try {
                val username = vm.signUp()
                showSnackBar("${getString(R.string.welcome)} $username")
                goToViewPagerFrag()
            } catch (e: MyException) {
                showSnackBar(e.message)
            }
            makeLayoutTouchable(true)
        }
    }

    private fun goToViewPagerFrag() {
        navigateTo(R.id.action_signUpFrag_to_viewPagerFrag)

    }

    fun cancel() {
        findNavController().popBackStack()
    }

}