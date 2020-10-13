package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.SignInFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag.hideKeyboard
import com.fruitPunchSamurai.firechat.others.MyFrag.makeLayoutTouchable
import com.fruitPunchSamurai.firechat.others.MyFrag.navigateTo
import com.fruitPunchSamurai.firechat.others.MyFrag.showSnackBar
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.viewModels.SignInViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignInFrag : Fragment() {

    private var b: SignInFragmentBinding? = null
    private val vm: SignInViewModel by viewModels()

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
        observeState()
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.sign_in_fragment,
            container,
            false
        ) as SignInFragmentBinding
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

    private fun goToViewPagerFrag() {
        navigateTo(R.id.action_signInFrag_to_viewPagerFrag)
    }

    fun goToSignUp() {
        navigateTo(R.id.action_signInFrag_to_signUpFrag)
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

    fun signInWithEmailAndPassword() {
        MainScope().launch {
            val username = vm.signInWithEmailAndPassword()
            if (username != null) goToViewPagerFrag()
        }
    }

}