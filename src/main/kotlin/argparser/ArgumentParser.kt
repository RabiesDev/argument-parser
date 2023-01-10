package argparser

import argparser.rules.Rule
import com.google.common.collect.ImmutableList

class ArgumentParser private constructor(
    private val prefix: String = "-",
    private val rules: ImmutableList<Rule>
) {
    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    fun parse(args: Array<String>): ArgumentContext {
        if (prefix.isEmpty() || rules.isEmpty() || args.isEmpty()) {
            throw IllegalStateException()
        }

        val joined = args.joinToString(separator = " ").lowercase()
        val options: HashMap<String, Any> = HashMap()
        rules.forEachIndexed { _, rule ->
            if (!rule.optional() && joined.indexOf(rule.name()) == -1) {
                throw IllegalArgumentException("Argument \"${rule.name()}\" is missing")
            }

            joined.split(prefix).forEach {
                val argBox = it.split(" ")
                if (rule.name() == argBox[0]) {
                    options[rule.name()] = argBox[1]
                }
            }
        }

        return ArgumentContext(options)
    }

    override fun toString(): String {
        val builder: StringBuilder = StringBuilder()
        builder.append("Options: ")
        builder.append("\n\r")

        rules.forEach {
            builder.append("    ")
            builder.append(prefix).append(it.toString())
            builder.append("\n\r")
        }
        return builder.toString()
    }

    class Builder {
        private val rules: ArrayList<Rule> = arrayListOf()
        private val prefix: String = "-"

        fun add(rule: Rule): Builder {
            rules.add(rule)
            return this
        }

        fun build(): ArgumentParser {
            return ArgumentParser(
                prefix,
                ImmutableList.builder<Rule>()
                    .addAll(rules)
                    .build()
            )
        }
    }
}
