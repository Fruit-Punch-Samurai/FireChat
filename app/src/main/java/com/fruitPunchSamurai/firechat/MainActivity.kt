package com.fruitPunchSamurai.firechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fruitPunchSamurai.firechat.databinding.ActivityMainBinding
import com.fruitPunchSamurai.firechat.others.PreferencesManager

private lateinit var b: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //TODO: Motion layout and Instant Modules

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        PreferencesManager.initialize(applicationContext)
    }
}