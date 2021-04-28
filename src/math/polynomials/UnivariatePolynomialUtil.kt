package math.polynomials

import math.linear.SquareMatrix
import math.ringsAndFields.*
import kotlin.math.max


fun <T: Ring<T>> T.toUnivariatePolynomial() = UnivariatePolynomial(this)

// region Operator extensions

// region Field case

operator fun <T: Field<T>> UnivariatePolynomial<T>.div(other: UnivariatePolynomial<T>): UnivariatePolynomial<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")

    val thisCoefficients = coefficients.subList(0, degree + 1).toMutableList()
    val otherCoefficients = other.coefficients.subList(0, other.degree + 1)
    val quotientCoefficients = ArrayList<T>()

    for (i in (thisCoefficients.size - otherCoefficients.size) downTo  0) {
        val quotient = thisCoefficients[otherCoefficients.lastIndex + i] / otherCoefficients.last()
        quotientCoefficients.add(quotient)
        otherCoefficients.forEachIndexed { index, t -> thisCoefficients[index + i] -= t * quotient }
    }

    return UnivariatePolynomial(quotientCoefficients, reverse = true)
}

operator fun <T: Field<T>> UnivariatePolynomial<T>.div(other: T): UnivariatePolynomial<T> =
    UnivariatePolynomial(
        coefficients
            .subList(0, degree + 1)
            .map { it / other }
    )

operator fun <T: Field<T>> UnivariatePolynomial<T>.rem(other: UnivariatePolynomial<T>): UnivariatePolynomial<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")

    val thisCoefficients = coefficients.subList(0, degree + 1).toMutableList()
    val otherCoefficients = other.coefficients.subList(0, other.degree + 1)

    for (i in (thisCoefficients.size - otherCoefficients.size) downTo 0) {
        val quotient = thisCoefficients[otherCoefficients.lastIndex + i] / otherCoefficients.last()
        otherCoefficients.forEachIndexed { index, t -> thisCoefficients[index + i] -= t * quotient }
    }

    return with(thisCoefficients) { UnivariatePolynomial(subList(0, max(this.indexOfLast { it.isNotZero() }, 0) + 1)) }
}

infix fun <T: Field<T>> UnivariatePolynomial<T>.divrem(other: UnivariatePolynomial<T>): UnivariatePolynomial.Companion.DividingResult<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")

    val thisCoefficients = coefficients.subList(0, degree + 1).toMutableList()
    val otherCoefficients = other.coefficients.subList(0, other.degree + 1)
    val quotientCoefficients = ArrayList<T>()

    for (i in (thisCoefficients.size - otherCoefficients.size) downTo  0) {
        val quotient = thisCoefficients[otherCoefficients.lastIndex + i] / otherCoefficients.last()
        quotientCoefficients.add(quotient)
        otherCoefficients.forEachIndexed { index, t -> thisCoefficients[index + i] -= t * quotient }
    }

    return UnivariatePolynomial.Companion.DividingResult(
        UnivariatePolynomial(quotientCoefficients, reverse = true),
        with(thisCoefficients) { UnivariatePolynomial(subList(0, max(this.indexOfLast { it.isNotZero() }, 0) + 1)) }
    )
}

fun <T: Field<T>> UnivariatePolynomial<T>.toMonicPolynomial() = UnivariatePolynomial(coefficients.map { it / coefficients.last() })

// endregion

// region Constants

operator fun <T: Ring<T>> T.plus(other: UnivariatePolynomial<T>) = other + this
operator fun <T: Ring<T>> Integer.plus(other: UnivariatePolynomial<T>) = other + this
operator fun <T: Ring<T>> Int.plus(other: UnivariatePolynomial<T>) = other + this
operator fun <T: Ring<T>> Long.plus(other: UnivariatePolynomial<T>) = other + this

operator fun <T: Ring<T>> T.minus(other: UnivariatePolynomial<T>) = -other + this
operator fun <T: Ring<T>> Integer.minus(other: UnivariatePolynomial<T>) = -other + this
operator fun <T: Ring<T>> Int.minus(other: UnivariatePolynomial<T>) = -other + this
operator fun <T: Ring<T>> Long.minus(other: UnivariatePolynomial<T>) = -other + this

operator fun <T: Ring<T>> T.times(other: UnivariatePolynomial<T>) = other * this
operator fun <T: Ring<T>> Integer.times(other: UnivariatePolynomial<T>) = other * this
operator fun <T: Ring<T>> Int.times(other: UnivariatePolynomial<T>) = other * this
operator fun <T: Ring<T>> Long.times(other: UnivariatePolynomial<T>) = other * this

// endregion

// region Polynomials

operator fun <T: Ring<T>> UnivariatePolynomial<T>.plus(other: UnivariateRationalFunction<T>) =
    UnivariateRationalFunction(
        this * other.denominator + other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> UnivariatePolynomial<T>.plus(other: Polynomial<T>) = this.toPolynomial() + other
operator fun <T: Ring<T>> UnivariatePolynomial<T>.plus(other: RationalFunction<T>) =
    RationalFunction(
        this.toPolynomial() * other.denominator + other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> UnivariatePolynomial<T>.plus(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() + other
operator fun <T: Ring<T>> UnivariatePolynomial<T>.plus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.denominator + other.numerator,
        other.denominator
    )

operator fun <T: Ring<T>> UnivariatePolynomial<T>.minus(other: UnivariateRationalFunction<T>) =
    UnivariateRationalFunction(
        this * other.denominator - other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> UnivariatePolynomial<T>.minus(other: Polynomial<T>) = this.toPolynomial() - other
operator fun <T: Ring<T>> UnivariatePolynomial<T>.minus(other: RationalFunction<T>) =
    RationalFunction(
        this.toPolynomial() * other.denominator - other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> UnivariatePolynomial<T>.minus(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() - other
operator fun <T: Ring<T>> UnivariatePolynomial<T>.minus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.denominator - other.numerator,
        other.denominator
    )

operator fun <T: Ring<T>> UnivariatePolynomial<T>.times(other: UnivariateRationalFunction<T>) =
    UnivariateRationalFunction(
        this * other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> UnivariatePolynomial<T>.times(other: Polynomial<T>) = this.toPolynomial() * other
operator fun <T: Ring<T>> UnivariatePolynomial<T>.times(other: RationalFunction<T>) =
    RationalFunction(
        this.toPolynomial() * other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> UnivariatePolynomial<T>.times(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() * other
operator fun <T: Ring<T>> UnivariatePolynomial<T>.times(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.numerator,
        other.denominator
    )

operator fun <T: Ring<T>> UnivariatePolynomial<T>.div(other: UnivariateRationalFunction<T>) =
    UnivariateRationalFunction(
        this * other.denominator,
        other.numerator
    )
operator fun <T: Field<T>> UnivariatePolynomial<T>.div(other: Polynomial<T>) = this.toPolynomial() / (other)
operator fun <T: Ring<T>> UnivariatePolynomial<T>.div(other: RationalFunction<T>) =
    RationalFunction(
        this.toPolynomial() * other.denominator,
        other.numerator
    )
operator fun <T: Field<T>> UnivariatePolynomial<T>.div(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() / other
operator fun <T: Ring<T>> UnivariatePolynomial<T>.div(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.denominator,
        other.numerator
    )

// endregion

// endregion

// region Invocation
/**
 * Returns numerator (polynomial) of rational function gotten by substitution rational function [arg] to the polynomial instance.
 * More concrete, if [arg] is `f(x)/g(x)` and the receiving instance is `p(x)`, then
 * ```
 * p(f/g) * g^deg(p)
 * ```
 * is returned.
 *
 * Used in [UnivariatePolynomial.invoke] and [UnivariateRationalFunction.invoke] for performance optimisation.
 */
internal infix fun <T: Ring<T>> UnivariatePolynomial<T>.invokeRFTakeNumerator (arg: UnivariateRationalFunction<T>): UnivariatePolynomial<T> =
    if (isZero()) this
    else
        coefficients
            .asSequence()
            .withIndex()
            .filter { it.value.isNotZero() }
            .map { (index, t) -> multiplyByPower(multiplyByPower(UnivariatePolynomial(t), arg.numerator, index), arg.denominator, degree - index) }
            .reduce { acc, res -> acc + res }
// endregion

// region Greatest Common Divisor (GCD) computation
fun <T: Field<T>> polynomialBinGCD(P: UnivariatePolynomial<T>, Q: UnivariatePolynomial<T>): UnivariatePolynomial<T> {
    println(P)
    println(Q)
    println()
    return if (Q.isZero()) P else polynomialBinGCD(Q, P % Q)
}

/**
 * Returns GCD (greatest common divider) of polynomials in [pols].
 *
 * Special cases:
 * - If [pols] is empty throws an exception.
 * - If any polynomial is 0 it is ignored. For example `gcd(P, 0) == P`.
 * - If [pols] is contains only zero polynomials) zero polynomial is returned.
 *
 * @param T Field where we are working now.
 * @param pols List of polynomials which GCD is asked.
 * @return GCD of the polynomials.
 */
fun <T: Field<T>> polynomialGCD(pols: List<UnivariatePolynomial<T>>): UnivariatePolynomial<T> = pols.reduce(::polynomialBinGCD)
fun <T: Field<T>> polynomialGCD(vararg pols: UnivariatePolynomial<T>): UnivariatePolynomial<T> = polynomialGCD(pols.toList())
// endregion

// region Interpolation
/**
 * Returns polynomial `P` of minimal degree such that `P(e.key) = e.value` for every `e` in entries of [results].
 *
 * @param T Field where we are working now.
 * @param results Describes values in specified points.
 * @return Interpolated polynomial.
 */
fun <T: Field<T>> interpolationPolynomial(results: Map<T, T>) =
    results
        .let { entries ->
            if (entries.isEmpty()) UnivariatePolynomial()
            else entries
                .map {
                    (results.keys - it.key)
                        .run {
                            if (this.isEmpty()) UnivariatePolynomial(it.value)
                            else this
                                .map { p: T -> UnivariatePolynomial(-p, p.getOne()) / (it.key - p) }
                                .reduce { acc, polynomial -> acc * polynomial } * it.value
                        }
                }
                .reduce { acc, polynomial -> acc + polynomial }
        }
fun <T: Field<T>> interpolationPolynomial(vararg results: Pair<T, T>) = interpolationPolynomial(mapOf(*results))
// endregion

// region Derivatives
/**
 * Returns result of applying formal derivative to the polynomial.
 *
 * @param T Field where we are working now.
 * @return Result of the operator.
 */
fun <T: Ring<T>> UnivariatePolynomial<T>.derivative() = UnivariatePolynomial(coefficients.mapIndexed { index, t -> t * index }.subList(1, coefficients.size))

/**
 * Returns result of applying discrete derivative to the polynomial with specified [delta].
 *
 * @param T Field where we are working now.
 * @param delta Argument shift in applying of the operator
 * @return Result of the operator.
 */
fun <T: Ring<T>> UnivariatePolynomial<T>.discreteDerivative(delta: T) = this(UnivariatePolynomial(delta, delta.getOne())) - this
// endregion

// region Resultant

fun <T: Ring<T>> resultant(P: UnivariatePolynomial<T>, Q: UnivariatePolynomial<T>) : T {
    val zero = P.ringZero
    val pCoefficients = P.coefficients.subList(0, P.degree + 1).reversed()
    val qCoefficients = Q.coefficients.subList(0, Q.degree + 1).reversed()
    val pDeg = P.degree
    val qDeg = Q.degree

    return SquareMatrix(
        List(qDeg) { List(it) { zero } + pCoefficients + List(qDeg - 1 - it) { zero } } +
                List(pDeg) { List(it) { zero } + qCoefficients + List(pDeg - 1 - it) { zero } }
    ).det
}

// endregion