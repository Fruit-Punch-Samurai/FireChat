package com.fruitPunchSamurai.firechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fruitPunchSamurai.firechat.databinding.ActivityMainBinding

private lateinit var b: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
    }
}