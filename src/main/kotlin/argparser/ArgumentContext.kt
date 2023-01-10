package argparser

import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class ArgumentContext(private val options: Map<String, Any>) {
    fun get(key: String): Any? {
        return options[key.lowercase()]
    }

    @OptIn(ExperimentalContracts::class)
    fun <T : Any> asAny(key: String, handler: (option: Optional<Any>) -> T?): Optional<T> {
        contract { callsInPlace(handler, InvocationKind.EXACTLY_ONCE) }
        return Optional.ofNullable(Optional.ofNullable(get(key)).let(handler))
    }

    fun asString(key: String): Optional<String> {
        return Optional.ofNullable(get(key) as String)
    }

    fun asBoolean(key: String): Optional<Boolean> {
        return Optional.ofNullable(get(key) as Boolean)
    }

    fun asDouble(key: String): Optional<Double> {
        return Optional.ofNullable(get(key) as Double)
    }

    fun asInt(key: String): Optional<Int> {
        return Optional.ofNullable(get(key) as Int)
    }

    fun asByte(key: String): Optional<Byte> {
        return Optional.ofNullable(get(key) as Byte)
    }
}
