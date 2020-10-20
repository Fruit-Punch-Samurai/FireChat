package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.MessagesFragmentBinding
import com.fruitPunchSamurai.firechat.databinding.MessagesRecyclerBinding
import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.viewModels.MessagesViewModel

class MessagesFrag : Fragment() {

    companion object {
        fun newInstance() = MessagesFrag()
    }

    private val vm by viewModels<MessagesViewModel>()
    private var b: MessagesFragmentBinding? = null
    lateinit var adapter: FirestoreRecyclerAdapter<LastMessage, Holder>

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
            R.layout.messages_fragment,
            container,
            false
        ) as MessagesFragmentBinding
        return b?.root
    }

    private fun bindData() {
        initiateAdapter()
        b?.apply {
            lifecycleOwner = viewLifecycleOwner
            frag = this@MessagesFrag
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    private fun initiateAdapter() {
        adapter = object : FirestoreRecyclerAdapter<LastMessage, Holder>(
            RecyclerOptions.getLastMessagesOption(viewLifecycleOwner, vm.getCurrentUserID()!!)
        ) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val binding = MessagesRecyclerBinding.inflate(LayoutInflater.from(parent.context))
                return Holder(binding)
            }

            override fun onBindViewHolder(holder: Holder, position: Int, model: LastMessage) {
                holder.bindData(model)
            }
        }
    }

    class Holder(private val b: MessagesRecyclerBinding) : RecyclerView.ViewHolder(b.root) {

        fun bindData(LastMessage: LastMessage) {
            b.lastMessage = LastMessage
            b.executePendingBindings()
        }
    }

}