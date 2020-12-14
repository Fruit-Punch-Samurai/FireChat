package com.fruitPunchSamurai.firechat.models

import com.google.firebase.firestore.ServerTimestamp
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

data class Message(
    var msg: String,
    var type: String,
    var mediaID: String,
    var ownerID: String,
    var date: String,
    @ServerTimestamp val tms: Date
) {
    constructor() : this("", "text", "", "", DateTime.now(DateTimeZone.UTC).toString(), Date())

    fun typeIsText() = type == "text"
    fun typeIsImage() = type == "image"
    fun typeIsVideo() = type == "video"
}