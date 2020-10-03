package com.fruitPunchSamurai.firechat.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val fragsList: ArrayList<Fragment>,
    fragMan: FragmentManager,
    life: Lifecycle
) :
    FragmentStateAdapter(fragMan, life) {

    override fun createFragment(position: Int): Fragment = fragsList[position]

    override fun getItemCount(): Int = fragsList.size

}