package com.fruitPunchSamurai.firechat.ext

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fruitPunchSamurai.firechat.models.LastMessage
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.repos.AuthRepo

object MyRecycler {

    @JvmStatic
    @BindingAdapter(value = ["setAdapter"])
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>?) {
        this.run {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["setMessageReadStatus"])
    fun ImageView.bindMessageReadState(lastMessage: LastMessage) {
        this.visibility = if (lastMessage.read) View.INVISIBLE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter(value = ["bindMyMessage"])
    fun TextView.bindMyMessage(message: Message) {
        this.run {
            this.apply {
                if (message.ownerID == AuthRepo().getUID() && message.isText()) {
                    text = message.msg
                    visibility = View.VISIBLE
                } else {
                    visibility = View.GONE
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bindMyImage"])
    fun ImageView.bindMyImage(message: Message) {
        this.run {
            visibility =
                if (message.ownerID == AuthRepo().getUID() && message.isImage()) View.VISIBLE
                else View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bindReceiverMessage"])
    fun TextView.bindReceiverMessage(message: Message) {
        this.run {
            if (message.ownerID != AuthRepo().getUID() && message.isText()) {
                text = message.msg
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bindReceiverImage"])
    fun ImageView.bindReceiverImage(message: Message) {
        this.run {
            visibility =
                if (message.ownerID != AuthRepo().getUID() && message.isImage()) View.VISIBLE
                else View.GONE
        }
    }

}