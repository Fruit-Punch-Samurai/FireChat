package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.adapters.ViewPagerAdapter
import com.fruitPunchSamurai.firechat.databinding.ViewPagerFragmentBinding
import com.fruitPunchSamurai.firechat.viewModels.ViewPagerViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFrag : Fragment() {

    companion object {
        fun newInstance() = ViewPagerFrag()
    }

    private var b: ViewPagerFragmentBinding? = null
    private val vm: ViewPagerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = initiateDataBinder(container)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initiateViewPager()
        initiateTabLayout()
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = ViewPagerFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    private fun initiateViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(MessagesFrag.newInstance())
        fragments.add(UsersFrag.newInstance())

        b?.viewPager?.adapter = ViewPagerAdapter(fragments, childFragmentManager, lifecycle)
        b?.viewPager?.offscreenPageLimit = 2
    }

    private fun initiateTabLayout() {
        TabLayoutMediator(b!!.tabLayout, b!!.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.messages)
                1 -> tab.text = getString(R.string.users)
            }
        }.attach()
    }


}