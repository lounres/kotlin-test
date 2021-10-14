package math.polynomials

import math.ringsAndFields.*


fun <T: Ring<T>> T.toRationalFunction() = RationalFunction(this.toPolynomial())

// region Operator extensions

// region Field case

fun <T: Field<T>> RationalFunction<T>.reduced(): RationalFunction<T> {
    val greatestCommonDivider = polynomialGCD(numerator, denominator)
    return RationalFunction(
        numerator / greatestCommonDivider,
        denominator / greatestCommonDivider
    )
}

// endregion

// region Constants

operator fun <T: Ring<T>> T.plus(other: RationalFunction<T>) = other + this
operator fun <T: Ring<T>> Integer.plus(other: RationalFunction<T>) = other + this
operator fun <T: Ring<T>> Int.plus(other: RationalFunction<T>) = other + this
operator fun <T: Ring<T>> Long.plus(other: RationalFunction<T>) = other + this

operator fun <T: Ring<T>> T.minus(other: RationalFunction<T>) = -other + this
operator fun <T: Ring<T>> Integer.minus(other: RationalFunction<T>) = -other + this
operator fun <T: Ring<T>> Int.minus(other: RationalFunction<T>) = -other + this
operator fun <T: Ring<T>> Long.minus(other: RationalFunction<T>) = -other + this

operator fun <T: Ring<T>> T.times(other: RationalFunction<T>) = other * this
operator fun <T: Ring<T>> Integer.times(other: RationalFunction<T>) = other * this
operator fun <T: Ring<T>> Int.times(other: RationalFunction<T>) = other * this
operator fun <T: Ring<T>> Long.times(other: RationalFunction<T>) = other * this

// endregion

// region Polynomials

operator fun <T: Ring<T>> RationalFunction<T>.plus(other: UnivariatePolynomial<T>) =
    RationalFunction(
        numerator + denominator * other.toPolynomial(),
        denominator
    )
operator fun <T: Ring<T>> RationalFunction<T>.plus(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        numerator * other.denominator.toPolynomial() + denominator * other.numerator.toPolynomial(),
        denominator * other.denominator.toPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.plus(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() + denominator.toLabeledPolynomial() * other,
        denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.plus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.denominator + denominator.toLabeledPolynomial() * other.numerator,
        denominator.toLabeledPolynomial() * other.denominator
    )

operator fun <T: Ring<T>> RationalFunction<T>.minus(other: UnivariatePolynomial<T>) =
    RationalFunction(
        numerator - denominator * other.toPolynomial(),
        denominator
    )
operator fun <T: Ring<T>> RationalFunction<T>.minus(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        numerator * other.denominator.toPolynomial() - denominator * other.numerator.toPolynomial(),
        denominator * other.denominator.toPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.minus(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() - denominator.toLabeledPolynomial() * other,
        denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.minus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.denominator - denominator.toLabeledPolynomial() * other.numerator,
        denominator.toLabeledPolynomial() * other.denominator
    )

operator fun <T: Ring<T>> RationalFunction<T>.times(other: UnivariatePolynomial<T>) =
    RationalFunction(
        numerator * other.toPolynomial(),
        denominator
    )
operator fun <T: Ring<T>> RationalFunction<T>.times(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        numerator * other.numerator.toPolynomial(),
        denominator * other.denominator.toPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.times(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other,
        denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.times(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.numerator,
        denominator.toLabeledPolynomial() * other.denominator
    )

operator fun <T: Ring<T>> RationalFunction<T>.div(other: UnivariatePolynomial<T>) =
    RationalFunction(
        numerator,
        denominator * other.toPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.div(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        numerator * other.denominator.toPolynomial(),
        denominator * other.numerator.toPolynomial()
    )
operator fun <T: Ring<T>> RationalFunction<T>.div(other: LabeledPolynomial<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial(),
        denominator.toLabeledPolynomial() * other
    )
operator fun <T: Ring<T>> RationalFunction<T>.div(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        numerator.toLabeledPolynomial() * other.denominator,
        denominator.toLabeledPolynomial() * other.numerator
    )

// endregion

// endregion