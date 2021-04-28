package math.ringsAndFields


// region Operator extensions
operator fun <T: Ring<T>> Integer.plus(other: T) = other + this
operator fun <T: Ring<T>> Int.plus(other: T) = other + this
operator fun <T: Ring<T>> Long.plus(other: T) = other + this

operator fun <T: Ring<T>> Integer.minus(other: T) = -other + this
operator fun <T: Ring<T>> Int.minus(other: T) = -other + this
operator fun <T: Ring<T>> Long.minus(other: T) = -other + this

operator fun <T: Ring<T>> Integer.times(other: T) = other * this
operator fun <T: Ring<T>> Int.times(other: T) = other * this
operator fun <T: Ring<T>> Long.times(other: T) = other * this
// endregion

// region Exponentiation
/**
 * Multiplies [input] with [base] in the [deg] power.
 * [deg] must be non-negative.
 * If [deg] is zero [input] is returned.
 *
 * Mathematically it can be written down like `input * base ^ deg`.
 *
 * It's applying [exponentiation by squaring](https://en.wikipedia.org/wiki/Exponentiation_by_squaring) to achieve the goal.
 *
 * @throws ArithmeticException If deg is negative.
 */
fun <T: Ring<T>> multiplyByPower(input: T, base: T, deg: Integer) : T =
    when {
        deg.isZero() -> input
        deg < 0 -> throw ArithmeticException("Can't divide in ring")
        (deg % 2).isZero() -> multiplyByPower(input, base * base, deg / 2)
        else -> multiplyByPower(input * base, base * base, deg / 2)
    }
fun <T: Ring<T>> multiplyByPower(input: T, base: T, deg: Int) : T =
    when {
        deg == 0 -> input
        deg < 0 -> throw ArithmeticException("Can't divide in ring")
        deg % 2 == 0 -> multiplyByPower(input, base * base, deg / 2)
        else -> multiplyByPower(input * base, base * base, deg / 2)
    }
fun <T: Ring<T>> multiplyByPower(input: T, base: T, deg: Long) : T =
    when {
        deg == 0L -> input
        deg < 0 -> throw ArithmeticException("Can't divide in ring")
        deg % 2 == 0L -> multiplyByPower(input, base * base, deg / 2)
        else -> multiplyByPower(input * base, base * base, deg / 2)
    }

/**
 * Raises [base] to the [deg] power.
 * [deg] must be non-negative.
 * If [deg] is zero one is returned.
 *
 * Mathematically it can be written down like `base ^ deg`.
 *
 * It's applying [exponentiation by squaring](https://en.wikipedia.org/wiki/Exponentiation_by_squaring) to achieve the goal.
 *
 * @throws ArithmeticException If deg is negative.
 */
fun <T: Ring<T>> power(base: T, deg: Integer) : T =
    when {
        deg.isZero() -> base.getOne()
        deg.isOne() -> base
        deg < 0 -> throw ArithmeticException("Can't divide in ring")
        (deg % 2).isZero() -> power(base * base, deg / 2)
        else -> power(base * base, deg / 2) * base
    }
fun <T: Ring<T>> power(base: T, deg: Int) : T =
    when {
        deg == 0 -> base.getOne()
        deg == 1 -> base
        deg < 0 -> throw ArithmeticException("Can't divide in ring")
        deg % 2 == 0 -> power(base * base, deg / 2)
        else -> power(base * base, deg / 2) * base
    }
fun <T: Ring<T>> power(base: T, deg: Long) : T =
    when {
        deg == 0L -> base.getOne()
        deg == 1L -> base
        deg < 0 -> throw ArithmeticException("Can't divide in ring")
        deg % 2 == 0L -> power(base * base, deg / 2)
        else -> power(base * base, deg / 2) * base
    }
// endregion