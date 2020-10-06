package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignUpFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.SignUpViewModel
import java.util.*

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
            val username = vm.getUsernameFromEmail().capitalize(Locale.getDefault())
            val sentence = getString(R.string.yourUsernameWillBe)
            vm.usernameSentence.value = "$sentence $username"
        })
    }

    fun signUp() {

    }

    fun cancel() {
        findNavController().popBackStack()
    }

}