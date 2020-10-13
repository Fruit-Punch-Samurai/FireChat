package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.databinding.UsersFragmentBinding
import com.fruitPunchSamurai.firechat.viewModels.UsersViewModel

class UsersFrag : Fragment() {

    companion object {
        fun newInstance() = UsersFrag()
    }

    private val vm: UsersViewModel by viewModels()
    private var b: UsersFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = initiateDataBinder(container)
        return view
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = UsersFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }
}