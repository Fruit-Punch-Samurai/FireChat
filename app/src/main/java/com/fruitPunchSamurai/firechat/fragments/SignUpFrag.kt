package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignUpFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag.hideKeyboard
import com.fruitPunchSamurai.firechat.others.MyFrag.makeLayoutTouchable
import com.fruitPunchSamurai.firechat.others.MyFrag.navigateTo
import com.fruitPunchSamurai.firechat.others.MyFrag.showSnackBar
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.viewModels.SignUpViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignUpFrag : Fragment() {

    private val vm: SignUpViewModel by viewModels()
    private var b: SignUpFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = initiateDataBinder(container)
        bindData()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeEmailChanges()
        observeState()
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
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

    private fun bindData() {
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
            val result = "$sentence $username"
            b?.signUpNameTxt?.text = result
        })
    }

    private fun observeState() {
        vm.state.observe(viewLifecycleOwner, {
            when (it) {
                is MyState.Finished -> {
                    showSnackBar(it.msg)
                    makeLayoutTouchable(true)
                    vm.setIdleState()
                }
                is MyState.Loading -> {
                    hideKeyboard()
                    makeLayoutTouchable(false)
                }
                is MyState.Error -> {
                    showSnackBar(it.msg)
                    makeLayoutTouchable(true)
                    vm.setIdleState()
                }
            }
        })
    }

    private fun goToViewPagerFrag() {
        navigateTo(R.id.action_signUpFrag_to_viewPagerFrag)
    }

    fun cancel() {
        findNavController().popBackStack()
    }

    fun signUp() {
        MainScope().launch {
            val username = vm.signUp()
            if (!username.isNullOrBlank()) {
                goToViewPagerFrag()
            }

        }
    }

}