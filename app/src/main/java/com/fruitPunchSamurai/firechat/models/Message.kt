package com.fruitPunchSamurai.firechat.models

import com.google.firebase.firestore.ServerTimestamp
import org.joda.time.DateTime
import java.util.*

data class Message(
    var msg: String,
    var ownerID: String,
    var date: DateTime,
    @ServerTimestamp val tms: Date
) {
    constructor() : this("", "", DateTime.now(), Date())
}