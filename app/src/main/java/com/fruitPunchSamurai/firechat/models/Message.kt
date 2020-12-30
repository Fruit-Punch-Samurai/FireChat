package com.fruitPunchSamurai.firechat.models

import com.fruitPunchSamurai.firechat.models.Message.Types.IMAGE
import com.fruitPunchSamurai.firechat.models.Message.Types.TEXT
import com.fruitPunchSamurai.firechat.models.Message.Types.VIDEO
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
    constructor() : this("", TEXT, "", "", DateTime.now(DateTimeZone.UTC).toString(), Date())

    companion object Fields {
        const val TIMESTAMP = "tms"
    }

    object Types {
        const val TEXT = "text"
        const val IMAGE = "image"
        const val VIDEO = "video"
    }

    fun typeIsText() = type == TEXT
    fun typeIsImage() = type == IMAGE
    fun typeIsVideo() = type == VIDEO
}