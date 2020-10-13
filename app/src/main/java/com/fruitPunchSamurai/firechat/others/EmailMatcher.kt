package com.fruitPunchSamurai.firechat.others

object EmailMatcher {
    fun emailIsWellFormatted(email: String) = email.matches(Regex(".+@\\w+[.]\\w+.*"))
}