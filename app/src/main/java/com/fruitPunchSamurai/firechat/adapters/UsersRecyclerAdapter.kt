package com.fruitPunchSamurai.firechat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.fruitPunchSamurai.firechat.databinding.UsersRecyclerBinding
import com.fruitPunchSamurai.firechat.models.User

class UsersRecyclerAdapter(options: FirestorePagingOptions<User>) :
    FirestorePagingAdapter<User, UsersRecyclerAdapter.ViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UsersRecyclerBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: User) {
        holder.bindData(model)
    }


    class ViewHolder(private val b: UsersRecyclerBinding) : RecyclerView.ViewHolder(b.root),
        View.OnClickListener {

        override fun onClick(v: View) {

        }

        fun bindData(user: User) {
            b.user = user
            b.executePendingBindings()
        }
    }

}
