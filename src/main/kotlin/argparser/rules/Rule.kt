package argparser.rules

interface Rule {
    fun name(): String

    fun optional(): Boolean
}
