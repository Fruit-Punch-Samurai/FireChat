package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.UsersFragmentBinding
import com.fruitPunchSamurai.firechat.databinding.UsersRecyclerBinding
import com.fruitPunchSamurai.firechat.ext.MyFrag.navigateTo
import com.fruitPunchSamurai.firechat.models.User
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.viewModels.UsersViewModel

class UsersFrag : Fragment() {

    companion object {
        fun newInstance() = UsersFrag()
    }

    private val vm: UsersViewModel by viewModels()
    private var b: UsersFragmentBinding? = null
    lateinit var adapter: FirestorePagingAdapter<User, Holder>

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
            R.layout.users_fragment,
            container,
            false
        ) as UsersFragmentBinding
        return b?.root
    }

    private fun bindData() {
        initiateAdapter()
        b?.apply {
            lifecycleOwner = viewLifecycleOwner
            frag = this@UsersFrag
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    fun goToChatFrag(user: User?) {
        if (user == null) return
        val receiverID = user.id
        val receiverName = user.name
        val action = ViewPagerFragDirections.actionViewPagerFragToChatFrag(receiverID, receiverName)
        navigateTo(action)
    }

    private fun initiateAdapter() {
        adapter = object : FirestorePagingAdapter<User, Holder>(
            RecyclerOptions.getAllUsersPagingOption(viewLifecycleOwner)
        ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val binding = UsersRecyclerBinding.inflate(LayoutInflater.from(parent.context))
                return Holder(binding) { goToChatFrag(binding.user) }
            }

            override fun onBindViewHolder(holder: Holder, position: Int, model: User) {
                holder.bindData(model)
            }
        }
    }

    class Holder(private val b: UsersRecyclerBinding, val onClickFun: () -> Unit) :
        RecyclerView.ViewHolder(b.root) {

        fun bindData(user: User) {
            b.user = user
            b.root.setOnClickListener { onClickFun() }
            b.executePendingBindings()
        }
    }

}