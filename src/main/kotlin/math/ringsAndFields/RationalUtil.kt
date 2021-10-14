package math.ringsAndFields


// region Operator extensions
operator fun Int.plus(other: Rational) = other + this
operator fun Long.plus(other: Rational) = other + this

operator fun Int.minus(other: Rational) = -other + this
operator fun Long.minus(other: Rational) = -other + this

operator fun Int.times(other: Rational) = other * this
operator fun Long.times(other: Rational) = other * this
// endregion

// region Conversion
/**
 * Returns the value of this number as [Rational].
 */
fun String.toRational() = Rational(this)
/**
 * Returns the value of this number as [Rational].
 */
fun Int.toRational() = Rational(this)
/**
 * Returns the value of this number as [Rational].
 */
fun Long.toRational() = Rational(this)
// endregion

fun abs(r: Rational) : Rational = Rational(
    abs(r.numerator),
    r.denominator,
    toCheckInput = false
)

/**
 * Return reciprocal of the instance in the [Rational] field.
 */
fun Rational.reciprocal() = Rational(denominator, numerator)