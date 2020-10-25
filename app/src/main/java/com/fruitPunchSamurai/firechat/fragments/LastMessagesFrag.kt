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
import com.fruitPunchSamurai.firechat.databinding.LastMessagesFragmentBinding
import com.fruitPunchSamurai.firechat.databinding.LastMessagesRecyclerBinding
import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.others.MyFrag.navigateTo
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.viewModels.LastMessagesViewModel

class LastMessagesFrag : Fragment() {

    companion object {
        fun newInstance() = LastMessagesFrag()
    }

    private val vm by viewModels<LastMessagesViewModel>()
    private var b: LastMessagesFragmentBinding? = null
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
            R.layout.last_messages_fragment,
            container,
            false
        ) as LastMessagesFragmentBinding
        return b?.root
    }

    private fun bindData() {
        initiateAdapter()
        b?.apply {
            lifecycleOwner = viewLifecycleOwner
            frag = this@LastMessagesFrag
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    fun goToChatFrag(lastMessage: LastMessage?) {
        if (lastMessage == null) return
        if (!lastMessage.read) vm.setMessageAsRead(lastMessage)
        val receiverID = lastMessage.contactID
        val receiverName = lastMessage.contactName
        val action = ViewPagerFragDirections.actionViewPagerFragToChatFrag(receiverID, receiverName)
        navigateTo(action)
    }

    private fun initiateAdapter() {
        adapter = object : FirestoreRecyclerAdapter<LastMessage, Holder>(
            RecyclerOptions.getLastMessagesOption(viewLifecycleOwner, vm.getCurrentUserID()!!)
        ) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val binding =
                    LastMessagesRecyclerBinding.inflate(LayoutInflater.from(parent.context))
                return Holder(binding) { goToChatFrag(binding.lastMessage) }
            }

            override fun onBindViewHolder(holder: Holder, position: Int, model: LastMessage) {
                holder.bindData(model)
            }
        }
    }

    class Holder(private val b: LastMessagesRecyclerBinding, private val clickFun: () -> Unit) :
        RecyclerView.ViewHolder(b.root),
        View.OnClickListener {

        override fun onClick(v: View?) {
            clickFun()
        }

        fun bindData(LastMessage: LastMessage) {
            b.root.setOnClickListener(this)
            b.lastMessage = LastMessage
            b.executePendingBindings()
        }
    }

}