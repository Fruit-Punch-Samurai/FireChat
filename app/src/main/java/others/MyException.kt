package others

class MyException(private val msg: String = "Undefined Error") : Exception() {

    override fun getLocalizedMessage(): String {
        return msg
    }

    override val message: String
        get() = msg
}
