package com.fruitPunchSamurai.firechat.fragments

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.databinding.UsersFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.viewModels.UsersViewModel

class UsersFrag : MyFrag() {

    companion object {
        fun newInstance() = UsersFrag()
    }

    private val vm: UsersViewModel by viewModels()
    private var b: UsersFragmentBinding? = null

    override fun initiateDataBinder(container: ViewGroup?): View? {
        b = UsersFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }
}