package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fruitPunchSamurai.firechat.R
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
        bindData()
        return view
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.users_fragment,
            container,
            false
        ) as UsersFragmentBinding
        return b?.root
    }

    private fun bindData() {
        b?.lifecycleOwner = viewLifecycleOwner
        b?.vm = vm
        b?.usersRecycler?.layoutManager = LinearLayoutManager(requireActivity())
        b?.adapter = vm.getRecyclerAdapter(viewLifecycleOwner)

    }
}