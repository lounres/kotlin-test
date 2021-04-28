package math.ringsAndFields


// region Operator extensions
operator fun Int.plus(other: Integer) = other + this
operator fun Long.plus(other: Integer) = other + this

operator fun Int.minus(other: Integer) = -other + this
operator fun Long.minus(other: Integer) = -other + this

operator fun Int.times(other: Integer) = other * this
operator fun Long.times(other: Integer) = other * this
// endregion

// region Conversion
/**
 * Returns the value of this number as [Integer].
 */
fun String.toInteger() = Integer(this)
/**
 * Returns the value of this number as [Integer].
 */
fun Int.toInteger() = Integer(this)
/**
 * Returns the value of this number as [Integer].
 */
fun Long.toInteger() = Integer(this)
// endregion

fun abs(n: Integer) : Integer = Integer(n.value.abs())