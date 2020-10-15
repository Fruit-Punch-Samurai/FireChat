package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.fruitPunchSamurai.firechat.adapters.UsersRecyclerAdapter
import com.fruitPunchSamurai.firechat.others.RecyclerOptions

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var adapter: UsersRecyclerAdapter

    /**Initializes the FirestorePagingAdapter if it has not been initialized*/
    fun initializeTheRecyclerAdapter(lifecycleOwner: LifecycleOwner) {
        if (!::adapter.isInitialized) adapter =
            UsersRecyclerAdapter(RecyclerOptions.getAllUsersPagingOption(lifecycleOwner))
    }
}