package com.fruitPunchSamurai.firechat

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fruitPunchSamurai.firechat.databinding.HomeFragmentBinding
import com.fruitPunchSamurai.firechat.viewModels.HomeViewModel

class HomeFrag : MyFrag() {

    private var b: HomeFragmentBinding? = null

    companion object {
        fun newInstance() = HomeFrag()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = HomeFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        Handler().postDelayed(
            { findNavController().navigate(R.id.action_homeFrag_to_viewPagerFrag) },
            3000
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}