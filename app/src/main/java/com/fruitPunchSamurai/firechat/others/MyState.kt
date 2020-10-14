package com.fruitPunchSamurai.firechat.others

sealed class MyState {

    object Idle : MyState()

    class Loading(val msg: String = "Loading") : MyState()

    class Finished(val msg: String = "") : MyState()

    class Error(val msg: String? = "Undefined error") : MyState()

}