package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.ChatFragmentBinding
import com.fruitPunchSamurai.firechat.databinding.ChatRecyclerBinding
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.viewModels.ChatViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatFrag : Fragment() {

    companion object {
        fun newInstance() = ChatFrag()
    }

    private val vm: ChatViewModel by viewModels()
    private var b: ChatFragmentBinding? = null
    private val args: ChatFragArgs by navArgs()

    private lateinit var receiverID: String
    private lateinit var receiverName: String
    lateinit var adapter: FirestoreRecyclerAdapter<Message, Holder>


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
        receiverID = args.receiverID
        receiverName = args.receiverName

        initiateRecyclerView()
        initiateAdapter()

        b?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            frag = this@ChatFrag
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    fun sendMessage() {
        GlobalScope.launch {
            vm.sendMessage(receiverID, receiverName)
        }
    }

    private fun initiateRecyclerView() {
        val man = LinearLayoutManager(requireActivity()).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        b?.recycler?.layoutManager = man
    }

    private fun initiateAdapter() {
        adapter = object : FirestoreRecyclerAdapter<Message, Holder>(
            RecyclerOptions.getMessagesOption(viewLifecycleOwner, vm.getCurrentUserID(), receiverID)
        ) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val binding = ChatRecyclerBinding.inflate(LayoutInflater.from(parent.context))
                return Holder(binding)
            }

            override fun onBindViewHolder(holder: Holder, position: Int, model: Message) {
                holder.bindData(model)
            }
        }
    }

    class Holder(private val b: ChatRecyclerBinding) : RecyclerView.ViewHolder(b.root) {

        fun bindData(Message: Message) {
            b.message = Message
            b.executePendingBindings()
        }
    }


}