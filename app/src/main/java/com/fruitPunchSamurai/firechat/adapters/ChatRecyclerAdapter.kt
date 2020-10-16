package com.fruitPunchSamurai.firechat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.fruitPunchSamurai.firechat.databinding.ChatRecyclerBinding
import com.fruitPunchSamurai.firechat.models.Message

class ChatRecyclerAdapter(options: FirestoreRecyclerOptions<Message>) :
    FirestoreRecyclerAdapter<Message, ChatRecyclerAdapter.ViewHolder>(options) {

    //TODO: SetOnClickListener in the ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatRecyclerBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Message) {
        holder.bindData(model)
    }


    class ViewHolder(private val b: ChatRecyclerBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bindData(Message: Message) {
            b.message = Message
            b.executePendingBindings()
        }
    }

}
