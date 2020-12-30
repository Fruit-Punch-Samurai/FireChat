package com.fruitPunchSamurai.firechat.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class LastMessage(
    var msg: String,
    var contactID: String,
    var contactName: String,
    var read: Boolean = true,
    @ServerTimestamp val tms: Date = Date()
) {
    constructor() : this("", "", "")
}