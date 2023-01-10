package argparser.rules

class BasicRule(
    private val name: String,
    private val optional: Boolean
) : Rule {
    override fun name(): String {
        return name;
    }

    override fun optional(): Boolean {
        return optional;
    }

    override fun toString(): String {
        return if (!optional) {
            "$name <value>"
        } else {
            "$name <value> (Optional)"
        }
    }
}
