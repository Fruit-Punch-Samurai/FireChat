package com.fruitPunchSamurai.firechat

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class Test {

    //Method can be found in Sign in and up
    private fun emailIsWellFormatted(email: String) = email.matches(Regex(".+@.*[.].*"))

    @Test
    fun emailIsWellFormatted() {
        var email = "batata@gmail.com"
        assertTrue(emailIsWellFormatted(email))

        email = "batata@gmail"
        assertFalse(emailIsWellFormatted(email))

        email = "batata@"
        assertFalse(emailIsWellFormatted(email))

        email = "batata"
        assertFalse(emailIsWellFormatted(email))

        email = "batata.com"
        assertFalse(emailIsWellFormatted(email))

        email = "batata.com@gmail"
        assertFalse(emailIsWellFormatted(email))

        email = "batata.com@gmail.com"
        assertTrue(emailIsWellFormatted(email))

        email = "@gmail"
        assertFalse(emailIsWellFormatted(email))

        email = "@gmail.com"
        assertFalse(emailIsWellFormatted(email))

    }
}