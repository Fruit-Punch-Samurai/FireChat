package com.fruitPunchSamurai.firechat.others

import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class DateConverter {

    private val humanFormatter = DateTimeFormat.forPattern("dd-MMMM-yyyy HH:mm")

    fun convertDateToLocal(rawDateTime: DateTime): String {
        return rawDateTime.toLocalDateTime()
            .toString(humanFormatter)
    }

    fun extractDate(convertedLocalDateTime: String): String {
        return convertedLocalDateTime.substring(0, convertedLocalDateTime.length - 6)
    }

    fun extractDate(convertedLocalDateTime: LocalDateTime): String {
        return convertedLocalDateTime.toLocalDate().toString()
    }

    fun extractTime(convertedLocalDateTime: String): String? {
        return convertedLocalDateTime.substring(convertedLocalDateTime.length - 5)
    }

    fun extractTime(convertedLocalDateTime: LocalDateTime): String? {
        return convertedLocalDateTime.toLocalTime().toString()
    }

}