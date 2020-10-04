package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.adapters.ViewPagerAdapter
import com.fruitPunchSamurai.firechat.databinding.ViewPagerFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.ViewPagerViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.view_pager_fragment.*

class ViewPagerFrag : MyFrag() {

    private var b: ViewPagerFragmentBinding? = null

    companion object {
        fun newInstance() = ViewPagerFrag()
    }

    private lateinit var viewModel: ViewPagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = ViewPagerFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewPagerViewModel::class.java)
        initiateViewPager()
        initiateTabLayout()
    }

    private fun initiateViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(MessagesFrag.newInstance())
        fragments.add(UsersFrag.newInstance())

        viewPager.adapter = ViewPagerAdapter(fragments, parentFragmentManager, lifecycle)
        viewPager.offscreenPageLimit = 2
    }

    private fun initiateTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.messages)
                1 -> tab.text = getString(R.string.users)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}