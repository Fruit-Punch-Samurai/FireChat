package com.fruitPunchSamurai.firechat.others

sealed class MyState {

    object Idle : MyState()

    object Loading : MyState()

    class Finished(val msg: String = "") : MyState()

    class Error(val msg: String = "Undefined error") : MyState()

}