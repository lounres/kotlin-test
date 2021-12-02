package math.varia.games

/**
 * Nimber (or nim-value, or Grundy value) class.
 */
data class Nimber (val value: UInt) : Comparable<Nimber> {
    operator fun unaryPlus() = this
    operator fun unaryMinus() = this
    operator fun plus(other: Nimber) = Nimber(this.value xor other.value)
    operator fun minus(other: Nimber) = Nimber(this.value xor other.value)
    operator fun inc() = Nimber(value + 1u)
    operator fun dec() = if (value == 0u) throw NimberException("Can't decrease zero nimber") else Nimber(value - 1u)
    override operator fun compareTo(other: Nimber) = value.compareTo(other.value)
    operator fun compareTo(other: UInt) = value.compareTo(other)
    operator fun compareTo(other: Int) = if (other < 0) 1 else value.compareTo(other.toUInt())

    override fun toString(): String {
        return "*$value"
    }

    internal class NimberException : Error {
        constructor() : super()
        constructor(message: String) : super(message)
    }

    companion object {
        fun valueOf(v: Int) = Nimber(v.toUInt())
        fun valueOf(v: UInt) = Nimber(v)
    }
}