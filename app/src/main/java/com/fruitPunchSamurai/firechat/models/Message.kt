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

    companion object Types {
        const val TEXT = "text"
        const val IMAGE = "image"
        const val VIDEO = "video"
    }

    fun typeIsText() = type == TEXT
    fun typeIsImage() = type == IMAGE
    fun typeIsVideo() = type == VIDEO
}