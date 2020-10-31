package com.fruitPunchSamurai.firechat

import com.fruitPunchSamurai.firechat.others.DateConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class DateTest {

    @Test
    fun convertDate() {
        val con = DateConverter()
        val date = "2020-10-28T17:21:11.626Z"

        assertEquals("28-10-2020", con.extractDate(date))
        assertEquals("18:21", con.extractTime(date))
        assertEquals("28-10-2020 18:21", con.extractDateAndTime(date))
    }
}
