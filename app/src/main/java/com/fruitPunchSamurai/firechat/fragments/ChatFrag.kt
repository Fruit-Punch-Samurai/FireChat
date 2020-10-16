package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.ChatFragmentBinding
import com.fruitPunchSamurai.firechat.others.MyFrag.navigateTo
import com.fruitPunchSamurai.firechat.viewModels.ChatViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ChatFrag : Fragment() {

    companion object {
        fun newInstance() = ChatFrag()
    }

    private val vm: ChatViewModel by viewModels()
    private var b: ChatFragmentBinding? = null
    private val receiverID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = initiateDataBinder(container)
        bindData()
        return view
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.chat_fragment,
            container,
            false
        ) as ChatFragmentBinding
        return b?.root
    }

    private fun bindData() {
        vm.initiateTheRecyclerAdapter(viewLifecycleOwner)
        b?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
        }

    }

    private fun goToChatFrag() {
        navigateTo(R.id.action_viewPagerFrag_to_chatFrag)
        println("Called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    fun sendMessage() {
        MainScope().launch {
            vm.sendMessage(receiverID)
        }
    }
}