package com.fruitPunchSamurai.firechat.others

class MyException(private val msg: String? = "Undefined Error") : Exception() {

    override fun getLocalizedMessage(): String {
        return msg ?: "Undefined Error"
    }

    override val message: String
        get() = msg ?: "Undefined Error"
}
