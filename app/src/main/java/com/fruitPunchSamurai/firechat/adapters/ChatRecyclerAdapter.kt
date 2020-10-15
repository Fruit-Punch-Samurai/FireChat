package com.fruitPunchSamurai.firechat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.fruitPunchSamurai.firechat.databinding.ChatRecyclerBinding
import com.fruitPunchSamurai.firechat.models.Message

class ChatRecyclerAdapter(options: FirestoreRecyclerOptions<Message>) :
    FirestoreRecyclerAdapter<Message, ChatRecyclerAdapter.ViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatRecyclerBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Message) {
        holder.bindData(model)
        println(model.msg)
        println(model.date)
        println(model.tms)
    }


    class ViewHolder(private val b: ChatRecyclerBinding) : RecyclerView.ViewHolder(b.root),
        View.OnClickListener {

        fun bindData(Message: Message) {
            b.message = Message
            b.executePendingBindings()
        }

        override fun onClick(v: View?) {

        }
    }

}
