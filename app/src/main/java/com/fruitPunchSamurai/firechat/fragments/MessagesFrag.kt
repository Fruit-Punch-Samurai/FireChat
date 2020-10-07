package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.databinding.MessagesFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.viewModels.MessagesViewModel

class MessagesFrag : MyFrag() {

    companion object {
        fun newInstance() = MessagesFrag()
    }

    private val vm: MessagesViewModel by viewModels()
    private var b: MessagesFragmentBinding? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        b?.button?.setOnClickListener { AuthRepo.logOut() }
    }

    override fun initiateDataBinder(container: ViewGroup?): View? {
        b = MessagesFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }
}