package com.fruitPunchSamurai.firechat.others

import org.joda.time.format.DateTimeFormat

class DateConverter {

    private val humanFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm")
    private val humanDateFormatter = DateTimeFormat.forPattern("dd-MM-yyyy")
    private val humanTimeFormatter = DateTimeFormat.forPattern("HH:mm")
    private val rawFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")

    fun extractDateAndTime(dateTime: String): String =
        rawFormatter.parseDateTime(dateTime).toLocalDateTime().toString(humanFormatter)


    fun extractDate(dateTime: String): String =
        rawFormatter.parseDateTime(dateTime).toLocalDate().toString(humanDateFormatter)


    fun extractTime(dateTime: String): String =
        rawFormatter.parseDateTime(dateTime).toLocalTime().toString(humanTimeFormatter)

}