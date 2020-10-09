package com.fruitPunchSamurai.firechat.others

class MyException(private val msg: String? = "Undefined Error") : Exception() {

    override fun getLocalizedMessage(): String {
        return msg.toString()
    }

    override val message: String
        get() = msg.toString()
}
