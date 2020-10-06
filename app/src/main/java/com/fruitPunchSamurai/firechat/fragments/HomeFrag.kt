package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.HomeFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.HomeViewModel

class HomeFrag : MyFrag() {

    private var b: HomeFragmentBinding? = null
    private val vm: HomeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Handler().postDelayed(
            {
                if (vm.userIsLoggedIn()) navigateTo(R.id.action_homeFrag_to_viewPagerFrag)
                else navigateTo(R.id.action_homeFrag_to_signInFrag)
            },
            2000
        )
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