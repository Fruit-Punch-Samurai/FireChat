package com.fruitPunchSamurai.firechat

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CommonTests {

    //Method can be found in Sign in and up
    /*Accepts emails addresses containing only a number*/
    private fun emailIsWellFormatted(email: String) = email.matches(Regex(".+@\\w+[.]\\w+.*"))

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

        email = "batata@gmail.com.uk"
        assertTrue(emailIsWellFormatted(email))

        email = "batata@gmail..com.uk"
        assertFalse(emailIsWellFormatted(email))

        email = "125batata@goku.com.uk"
        assertTrue(emailIsWellFormatted(email))

        email = "bat124ta125@gmail.com.uk"
        assertTrue(emailIsWellFormatted(email))

        email = "batata125@gmail.com"
        assertTrue(emailIsWellFormatted(email))

    }
}