package math.polynomials

import math.ringsAndFields.*


/**
 * Converts the variable [this] into monomial with coefficient [c] as [LabeledPolynomial].
 */
fun <T: Ring<T>> Variable.toLabeledPolynomial(c: T): LabeledPolynomial<T> =
    LabeledPolynomial(
        mapOf(this to 1) to c
    )

/**
 * Converts the variable [this] into monomial with coefficient [c] as [LabeledRationalFunction].
 */
fun <T: Ring<T>> Variable.toLabeledRationalFunction(c: T): LabeledRationalFunction<T> =
    LabeledRationalFunction(
        this.toLabeledPolynomial(c)
    )

// region Operator extensions
/**
 *
 */
operator fun <T: Ring<T>> Variable.plus(other: T): LabeledPolynomial<T> = toLabeledPolynomial(other.getOne()) + other
operator fun <T: Ring<T>> T.plus(other: Variable): LabeledPolynomial<T> = this + other.toLabeledPolynomial(getOne())
operator fun <T: Ring<T>> Variable.plus(other: LabeledPolynomial<T>): LabeledPolynomial<T> = toLabeledPolynomial(other.ringOne) + other
operator fun <T: Ring<T>> LabeledPolynomial<T>.plus(other: Variable): LabeledPolynomial<T> = this + other.toLabeledPolynomial(ringOne)
operator fun <T: Ring<T>> Variable.plus(other: LabeledRationalFunction<T>): LabeledRationalFunction<T> = toLabeledPolynomial(other.ringOne) + other
operator fun <T: Ring<T>> LabeledRationalFunction<T>.plus(other: Variable): LabeledRationalFunction<T> = this + other.toLabeledPolynomial(ringOne)

operator fun <T: Ring<T>> Variable.minus(other: T): LabeledPolynomial<T> = toLabeledPolynomial(other.getOne()) - other
operator fun <T: Ring<T>> T.minus(other: Variable): LabeledPolynomial<T> = this - other.toLabeledPolynomial(getOne())
operator fun <T: Ring<T>> Variable.minus(other: LabeledPolynomial<T>): LabeledPolynomial<T> = toLabeledPolynomial(other.ringOne) - other
operator fun <T: Ring<T>> LabeledPolynomial<T>.minus(other: Variable): LabeledPolynomial<T> = this - other.toLabeledPolynomial(ringOne)
operator fun <T: Ring<T>> Variable.minus(other: LabeledRationalFunction<T>): LabeledRationalFunction<T> = toLabeledPolynomial(other.ringOne) - other
operator fun <T: Ring<T>> LabeledRationalFunction<T>.minus(other: Variable): LabeledRationalFunction<T> = this - other.toLabeledPolynomial(ringOne)

operator fun <T: Ring<T>> Variable.times(other: T): LabeledPolynomial<T> = toLabeledPolynomial(other)
operator fun <T: Ring<T>> T.times(other: Variable): LabeledPolynomial<T> = other.toLabeledPolynomial(this)
operator fun <T: Ring<T>> Variable.times(other: LabeledPolynomial<T>): LabeledPolynomial<T> =
    if (other.isZero()) other
    else
        LabeledPolynomial(
            other.coefficients
                .mapKeys { (degs, _) -> degs.toMutableMap().apply { this[this@times] = getOrDefault(this@times, 0) + 1 } }
        )
operator fun <T: Ring<T>> LabeledPolynomial<T>.times(other: Variable): LabeledPolynomial<T> =
    if (isZero()) this
    else
        LabeledPolynomial(
            coefficients
                .mapKeys { (degs, _) -> degs.toMutableMap().apply { this[other] = getOrDefault(other, 0) + 1 } }
        )
operator fun <T: Ring<T>> Variable.times(other: LabeledRationalFunction<T>): LabeledRationalFunction<T> =
    LabeledRationalFunction(
        this * other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> LabeledRationalFunction<T>.times(other: Variable): LabeledRationalFunction<T> =
    LabeledRationalFunction(
        numerator * other,
        denominator
    )

// TODO: Может ещё что-то придумается?
// TODO: Систематизировать?

// endregion