package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.HomeFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.HomeViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HomeFrag : MyFrag() {

    private var b: HomeFragmentBinding? = null
    private val vm: HomeViewModel by viewModels()

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

    override fun initiateDataBinder(container: ViewGroup?): View? {
        b = HomeFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}