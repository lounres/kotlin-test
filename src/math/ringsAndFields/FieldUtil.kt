package math.ringsAndFields


/**
 * Return reciprocal of the instance in the field [T].
 *
 * @receiver The considered field where operation is processed.
 */
fun <T: Field<T>> T.reciprocal() = this.getOne() / this

// region Exponentiation
/**
 * Multiplies [input] with [base] in the [deg] power.
 * If [deg] is zero [input] is returned.
 * If [deg] is negative reciprocal of [base] is used.
 *
 * Mathematically it can be written down like `input * base ^ deg`.
 *
 * It's applying [exponentiation by squaring](https://en.wikipedia.org/wiki/Exponentiation_by_squaring) to achieve the goal.
 */
fun <T: Field<T>> multiplyByPower(input: T, base: T, deg: Integer) : T =
    when {
        deg.isZero() -> input
        deg < 0 -> multiplyByPower(input, base.reciprocal(), -deg)
        (deg % 2).isZero() -> multiplyByPower(input, base * base, deg / 2)
        else -> multiplyByPower(input * base, base * base, deg / 2)
    }
fun <T: Field<T>> multiplyByPower(input: T, base: T, deg: Int) : T =
    when {
        deg == 0 -> input
        deg < 0 -> multiplyByPower(input, base.reciprocal(), -deg)
        deg % 2 == 0 -> multiplyByPower(input, base * base, deg / 2)
        else -> multiplyByPower(input * base, base * base, deg / 2)
    }
fun <T: Field<T>> multiplyByPower(input: T, base: T, deg: Long) : T =
    when {
        deg == 0L -> input
        deg < 0 -> multiplyByPower(input, base.reciprocal(), -deg)
        deg % 2 == 0L -> multiplyByPower(input, base * base, deg / 2)
        else -> multiplyByPower(input * base, base * base, deg / 2)
    }

/**
 * Raises [base] to the [deg] power.
 * If [deg] is zero one is returned.
 * If [deg] is negative reciprocal of [base] is used.
 *
 * Mathematically it can be written down like `base ^ deg`.
 *
 * It's applying [exponentiation by squaring](https://en.wikipedia.org/wiki/Exponentiation_by_squaring) to achieve the goal.
 */
fun <T: Field<T>> power(base: T, deg: Integer) : T =
    when {
        deg.isZero() -> base.getOne()
        deg.isOne() -> base
        deg < 0 -> power(base.reciprocal(), -deg)
        (deg % 2).isZero() -> power(base * base, deg / 2)
        else -> power(base * base, deg / 2) * base
    }
fun <T: Field<T>> power(base: T, deg: Int) : T =
    when {
        deg == 0 -> base.getOne()
        deg == 1 -> base
        deg < 0 -> power(base.reciprocal(), -deg)
        deg % 2 == 0 -> power(base * base, deg / 2)
        else -> power(base * base, deg / 2) * base
    }
fun <T: Field<T>> power(base: T, deg: Long) : T =
    when {
        deg == 0L -> base.getOne()
        deg == 1L -> base
        deg < 0 -> power(base.reciprocal(), -deg)
        deg % 2 == 0L -> power(base * base, deg / 2)
        else -> power(base * base, deg / 2) * base
    }
// endregion