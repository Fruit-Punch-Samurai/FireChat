package com.fruitPunchSamurai.firechat.others

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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
    @BindingAdapter(value = ["bindMyMessage"])
    fun TextView.bindMyMessage(message: Message) {
        this.run {
            visibility = if (message.ownerID == AuthRepo().getUID()) View.VISIBLE
            else View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bindReceiverMessage"])
    fun TextView.bindReceiverMessage(message: Message) {
        this.run {
            visibility = if (message.ownerID != AuthRepo().getUID()) View.VISIBLE
            else View.GONE
        }
    }

}