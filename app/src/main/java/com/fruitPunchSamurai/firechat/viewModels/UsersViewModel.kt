package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.fruitPunchSamurai.firechat.adapters.UsersRecyclerAdapter
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.repos.MainRepo

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepo = MainRepo()
    lateinit var adapter: UsersRecyclerAdapter

    /**Initializes the FirestoreAdapter if it has not been initialized*/
    fun initializeTheRecyclerAdapter(lifecycleOwner: LifecycleOwner) {
        if (!this::adapter.isInitialized) adapter =
            UsersRecyclerAdapter(RecyclerOptions.getAllUsersPagingOption(lifecycleOwner))
    }
}