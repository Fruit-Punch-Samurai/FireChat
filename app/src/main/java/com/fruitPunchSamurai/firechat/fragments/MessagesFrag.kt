package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.databinding.MessagesFragmentBinding
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.viewModels.MessagesViewModel

class MessagesFrag : Fragment() {

    companion object {
        fun newInstance() = MessagesFrag()
    }

    private val vm by viewModels<MessagesViewModel>()
    private val auth = AuthRepo()
    private var b: MessagesFragmentBinding? = null

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

        b?.button?.setOnClickListener { auth.logOut() }
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = MessagesFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

}