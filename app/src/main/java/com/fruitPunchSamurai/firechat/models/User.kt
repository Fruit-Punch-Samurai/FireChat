package com.fruitPunchSamurai.firechat.models

data class User(var id: String, var name: String) {
    constructor() : this("", "")

    companion object Fields {
        const val NAME = "name"
    }
}