package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.adapters.ViewPagerAdapter
import com.fruitPunchSamurai.firechat.databinding.ViewPagerFragmentBinding
import com.fruitPunchSamurai.firechat.ext.MyFrag.navigateTo
import com.fruitPunchSamurai.firechat.viewModels.ViewPagerViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFrag : Fragment() {

    companion object;

    private var b: ViewPagerFragmentBinding? = null
    private val vm: ViewPagerViewModel by viewModels()

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
        initiateViewPager()
        initiateTabLayout()
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.view_pager_fragment,
            container,
            false
        ) as ViewPagerFragmentBinding
        return b?.root
    }

    private fun bindData() {
        b?.apply {
            frag = this@ViewPagerFrag
            viewModel = vm
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    fun logout() {
        vm.logout()
        navigateTo(R.id.action_viewPagerFrag_to_signInFrag2)
    }

    private fun initiateViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(LastMessagesFrag.newInstance())
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