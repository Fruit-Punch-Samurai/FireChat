package com.fruitPunchSamurai.firechat.models

import com.google.firebase.firestore.ServerTimestamp
import org.joda.time.DateTime
import java.util.*

data class Message(val msg: String, val date: DateTime, @ServerTimestamp val tms: Date) {
}