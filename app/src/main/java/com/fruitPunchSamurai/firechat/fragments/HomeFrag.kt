package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.HomeFragmentBinding
import com.fruitPunchSamurai.firechat.ext.MyFrag.navigateTo
import com.fruitPunchSamurai.firechat.viewModels.HomeViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HomeFrag : Fragment() {

    private var b: HomeFragmentBinding? = null
    private val vm: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initiateDataBinder(container)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        MainScope().launch {
            if (!vm.userIsLoggedIn() || !vm.userExistsInDatabase()) {
                vm.logout()
                navigateTo(R.id.action_homeFrag_to_signInFrag)
                return@launch
            }

            navigateTo(R.id.action_homeFrag_to_viewPagerFrag)

        }
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = HomeFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}