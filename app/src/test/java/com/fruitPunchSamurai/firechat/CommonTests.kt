package com.fruitPunchSamurai.firechat

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CommonTests {

    //Method can be found in Sign in and up
    /*Accepts emails addresses containing only a number*/
    private fun email_is_well_formatted(email: String) = email.matches(Regex(".+@\\w+[.]\\w+.*"))

    @Test
    fun email_is_well_formatted() {
        var email = "batata@gmail.com"
        assertTrue(email_is_well_formatted(email))

        email = "batata@"
        assertFalse(email_is_well_formatted(email))

        email = "batata"
        assertFalse(email_is_well_formatted(email))

        email = "batata.com"
        assertFalse(email_is_well_formatted(email))

        email = "batata.com@gmail"
        assertFalse(email_is_well_formatted(email))

        email = "batata.com@gmail.com"
        assertTrue(email_is_well_formatted(email))

        email = "@gmail"
        assertFalse(email_is_well_formatted(email))

        email = "@gmail.com"
        assertFalse(email_is_well_formatted(email))

        email = "batata@gmail.com.uk"
        assertTrue(email_is_well_formatted(email))

        email = "batata@gmail..com.uk"
        assertFalse(email_is_well_formatted(email))

        email = "125batata@goku.com.uk"
        assertTrue(email_is_well_formatted(email))

        email = "bat124ta125@gmail.com.uk"
        assertTrue(email_is_well_formatted(email))

        email = "batata125@gmail.com"
        assertTrue(email_is_well_formatted(email))

        email = "45315@gmail.com"
        assertTrue(email_is_well_formatted(email))
    }

}