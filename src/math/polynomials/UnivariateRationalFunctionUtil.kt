package math.polynomials

import math.ringsAndFields.*


fun <T: Ring<T>> T.toUnivariateRationalFunction() = UnivariateRationalFunction(this.toUnivariatePolynomial())

// region Operator extensions

// region Field case

operator fun <T: Field<T>> UnivariateRationalFunction<T>.invoke(arg: T): T = numerator(arg) / denominator(arg)

fun <T: Field<T>> UnivariateRationalFunction<T>.reduced(): UnivariateRationalFunction<T> {
    val greatestCommonDivider = polynomialGCD(numerator, denominator)
    return UnivariateRationalFunction(
        numerator / greatestCommonDivider,
        denominator / greatestCommonDivider
    )
}

// endregion

// region Constants

operator fun <T: Ring<T>> T.plus(other: UnivariateRationalFunction<T>) = other + this
operator fun <T: Ring<T>> Integer.plus(other: UnivariateRationalFunction<T>) = other + this
operator fun <T: Ring<T>> Int.plus(other: UnivariateRationalFunction<T>) = other + this
operator fun <T: Ring<T>> Long.plus(other: UnivariateRationalFunction<T>) = other + this

operator fun <T: Ring<T>> T.minus(other: UnivariateRationalFunction<T>) = -other + this
operator fun <T: Ring<T>> Integer.minus(other: UnivariateRationalFunction<T>) = -other + this
operator fun <T: Ring<T>> Int.minus(other: UnivariateRationalFunction<T>) = -other + this
operator fun <T: Ring<T>> Long.minus(other: UnivariateRationalFunction<T>) = -other + this

operator fun <T: Ring<T>> T.times(other: UnivariateRationalFunction<T>) = other * this
operator fun <T: Ring<T>> Integer.times(other: UnivariateRationalFunction<T>) = other * this
operator fun <T: Ring<T>> Int.times(other: UnivariateRationalFunction<T>) = other * this
operator fun <T: Ring<T>> Long.times(other: UnivariateRationalFunction<T>) = other * this

// endregion

// region Polynomials

operator fun <T: Ring<T>> UnivariateRationalFunction<T>.plus(other: Polynomial<T>) =
    RationalFunction(
        numerator.toPolynomial() + denominator.toPolynomial() * other,
        denominator.toPolynomial()
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.plus(other: RationalFunction<T>) =
    RationalFunction(
        numerator.toPolynomial() * other.denominator + denominator.toPolynomial() * other.numerator,
        denominator.toPolynomial() * other.denominator
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.plus(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() + denominator.toLabeledPolynomial() * other,
        denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.plus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.denominator + denominator.toLabeledPolynomial() * other.numerator,
        denominator.toLabeledPolynomial() * other.denominator
    )

operator fun <T: Ring<T>> UnivariateRationalFunction<T>.minus(other: Polynomial<T>) =
    RationalFunction(
        numerator.toPolynomial() - denominator.toPolynomial() * other,
        denominator.toPolynomial()
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.minus(other: RationalFunction<T>) =
    RationalFunction(
        numerator.toPolynomial() * other.denominator - denominator.toPolynomial() * other.numerator,
        denominator.toPolynomial() * other.denominator
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.minus(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() - denominator.toLabeledPolynomial() * other,
        denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.minus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.denominator - denominator.toLabeledPolynomial() * other.numerator,
        denominator.toLabeledPolynomial() * other.denominator
    )

operator fun <T: Ring<T>> UnivariateRationalFunction<T>.times(other: Polynomial<T>) =
    RationalFunction(
        numerator.toPolynomial() * other,
        denominator.toPolynomial()
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.times(other: RationalFunction<T>) =
    RationalFunction(
        numerator.toPolynomial() * other.numerator,
        denominator.toPolynomial() * other.denominator
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.times(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other,
        denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.times(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.numerator,
        denominator.toLabeledPolynomial() * other.denominator
    )

operator fun <T: Ring<T>> UnivariateRationalFunction<T>.div(other: Polynomial<T>) =
    RationalFunction(
        numerator.toPolynomial(),
        denominator.toPolynomial() * other
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.div(other: RationalFunction<T>) =
    RationalFunction(
        numerator.toPolynomial() * other.denominator,
        denominator.toPolynomial() * other.numerator
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.div(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial(),
        denominator.toLabeledPolynomial() * other
    )
operator fun <T: Ring<T>> UnivariateRationalFunction<T>.div(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.denominator,
        denominator.toLabeledPolynomial() * other.numerator
    )

// endregion

// endregion

// region Derivatives
/**
 * Returns result of applying formal derivative to the polynomial.
 *
 * @param T Field where we are working now.
 * @return Result of the operator.
 */
fun <T: Ring<T>> UnivariateRationalFunction<T>.derivative() =
    UnivariateRationalFunction(
        numerator.derivative() * denominator - denominator.derivative() * numerator,
        denominator * denominator
    )
// endregion