package math.varia.games.old

/**
 * Nimber (or nim-value, or Grundy value) class.
 */
data class Nimber(val value: Int) {

    // region Operators
    operator fun unaryPlus() = this
    operator fun unaryMinus() = this
    operator fun plus(other: Nimber) = Nimber(this.value xor other.value)
    operator fun minus(other: Nimber) = Nimber(this.value xor other.value)
    operator fun compareTo(other: Nimber) = value - other.value
    operator fun compareTo(other: Int) = value - other
    // endregion

    override fun toString(): String {
        return "*" + this.value.toString()
    }
}

operator fun Int.compareTo(other: Nimber) = this - other.value