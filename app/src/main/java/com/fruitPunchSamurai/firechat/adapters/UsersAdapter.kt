package com.fruitPunchSamurai.firechat.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fruitPunchSamurai.firechat.databinding.UsersRecyclerBinding
import com.fruitPunchSamurai.firechat.models.User

class UsersAdapter(var userList: ArrayList<User>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UsersRecyclerBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(userList[position])
    }

    override fun getItemCount() = userList.size


    class ViewHolder(val b: UsersRecyclerBinding) : RecyclerView.ViewHolder(b.root),
        View.OnClickListener {

        override fun onClick(v: View) {
            Log.i("OnClickListener", "Clicked")
        }

        fun bindData(user: User) {
            b.user = user
            b.executePendingBindings()
        }

    }
}
