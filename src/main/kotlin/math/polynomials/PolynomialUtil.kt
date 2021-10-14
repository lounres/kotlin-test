package math.polynomials

import math.ringsAndFields.*
import math.polynomials.Polynomial.Companion.cleanUp
import java.lang.Error
import kotlin.math.max


fun <T: Ring<T>> T.toPolynomial() = Polynomial(listOf<Int>() to this, toCheckInput = false)

// region Operator extensions

// region Field case

operator fun <T: Field<T>> Polynomial<T>.div(other: Polynomial<T>): Polynomial<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")
    if (isZero()) return this

    fun Map<List<Int>, T>.leadingTerm() =
        this
            .asSequence()
            .map { Pair(it.key, it.value) }
            .reduce { (accDegs, accC), (listDegs, listC) ->
                for (i in 0..accDegs.lastIndex) {
                    if (accDegs[i] > listDegs.getOrElse(i) { 0 }) return@reduce accDegs to accC
                    if (accDegs[i] < listDegs.getOrElse(i) { 0 }) return@reduce listDegs to listC
                }
                if (accDegs.size < listDegs.size) listDegs to listC else accDegs to accC
            }

    var thisCoefficients = coefficients.toMutableMap()
    val otherCoefficients = other.coefficients
    val quotientCoefficients = HashMap<List<Int>, T>()

    var (thisLeadingTermDegs, thisLeadingTermC) = thisCoefficients.leadingTerm()
    val (otherLeadingTermDegs, otherLeadingTermC) = otherCoefficients.leadingTerm()

    while (
        thisLeadingTermDegs.size >= otherLeadingTermDegs.size &&
        (0..otherLeadingTermDegs.lastIndex).all { thisLeadingTermDegs[it] >= otherLeadingTermDegs[it] }
    ) {
        val multiplierDegs =
            thisLeadingTermDegs
                .mapIndexed { index, deg -> deg - otherLeadingTermDegs.getOrElse(index) { 0 } }
                .cleanUp()
        val multiplierC = thisLeadingTermC / otherLeadingTermC

        quotientCoefficients[multiplierDegs] = multiplierC

        for ((degs, t) in otherCoefficients) {
            val productDegs =
                (0..max(degs.lastIndex, multiplierDegs.lastIndex))
                    .map { degs.getOrElse(it) { 0 } + multiplierDegs.getOrElse(it) { 0 } }
                    .cleanUp()
            val productC = t * multiplierC
            thisCoefficients[productDegs] =
                if (productDegs in thisCoefficients) thisCoefficients[productDegs]!! - productC else -productC
        }

        thisCoefficients = thisCoefficients.filterValues { it.isNotZero() }.toMutableMap()

        if (thisCoefficients.isEmpty())
            return Polynomial(quotientCoefficients, toCheckInput = false)

        val t = thisCoefficients.leadingTerm()
        thisLeadingTermDegs = t.first
        thisLeadingTermC = t.second
    }

    return Polynomial(quotientCoefficients, toCheckInput = false)
}

operator fun <T: Field<T>> Polynomial<T>.div(other: T): Polynomial<T> =
    if (other.isZero()) throw ArithmeticException("/ by zero")
    else
        Polynomial(
            coefficients
                .mapValues { it.value / other },
            toCheckInput = false
        )

operator fun <T: Field<T>> Polynomial<T>.rem(other: Polynomial<T>): Polynomial<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")
    if (isZero()) return this

    fun Map<List<Int>, T>.leadingTerm() =
        this
            .asSequence()
            .map { Pair(it.key, it.value) }
            .reduce { (accDegs, accC), (listDegs, listC) ->
                for (i in 0..accDegs.lastIndex) {
                    if (accDegs[i] > listDegs.getOrElse(i) { 0 }) return@reduce accDegs to accC
                    if (accDegs[i] < listDegs.getOrElse(i) { 0 }) return@reduce listDegs to listC
                }
                if (accDegs.size < listDegs.size) listDegs to listC else accDegs to accC
            }

    var thisCoefficients = coefficients.toMutableMap()
    val otherCoefficients = other.coefficients

    var (thisLeadingTermDegs, thisLeadingTermC) = thisCoefficients.leadingTerm()
    val (otherLeadingTermDegs, otherLeadingTermC) = otherCoefficients.leadingTerm()

    while (
        thisLeadingTermDegs.size >= otherLeadingTermDegs.size &&
        (0..otherLeadingTermDegs.lastIndex).all { thisLeadingTermDegs[it] >= otherLeadingTermDegs[it] }
    ) {
        val multiplierDegs =
            thisLeadingTermDegs
                .mapIndexed { index, deg -> deg - otherLeadingTermDegs.getOrElse(index) { 0 } }
                .cleanUp()
        val multiplierC = thisLeadingTermC / otherLeadingTermC

        for ((degs, t) in otherCoefficients) {
            val productDegs =
                (0..max(degs.lastIndex, multiplierDegs.lastIndex))
                    .map { degs.getOrElse(it) { 0 } + multiplierDegs.getOrElse(it) { 0 } }
                    .cleanUp()
            val productC = t * multiplierC
            thisCoefficients[productDegs] =
                if (productDegs in thisCoefficients) thisCoefficients[productDegs]!! - productC else -productC
        }

        thisCoefficients = thisCoefficients.filterValues { it.isNotZero() }.toMutableMap()

        if (thisCoefficients.isEmpty())
            return Polynomial(thisCoefficients, toCheckInput = false)

        val t = thisCoefficients.leadingTerm()
        thisLeadingTermDegs = t.first
        thisLeadingTermC = t.second
    }

    return Polynomial(thisCoefficients, toCheckInput = false)
}

infix fun <T: Field<T>> Polynomial<T>.divrem(other: Polynomial<T>): Polynomial.Companion.DividingResult<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")
    if (isZero()) return Polynomial.Companion.DividingResult(this, this)

    fun Map<List<Int>, T>.leadingTerm() =
        this
            .asSequence()
            .map { Pair(it.key, it.value) }
            .reduce { (accDegs, accC), (listDegs, listC) ->
                for (i in 0..accDegs.lastIndex) {
                    if (accDegs[i] > listDegs.getOrElse(i) { 0 }) return@reduce accDegs to accC
                    if (accDegs[i] < listDegs.getOrElse(i) { 0 }) return@reduce listDegs to listC
                }
                if (accDegs.size < listDegs.size) listDegs to listC else accDegs to accC
            }

    var thisCoefficients = coefficients.toMutableMap()
    val otherCoefficients = other.coefficients
    val quotientCoefficients = HashMap<List<Int>, T>()

    var (thisLeadingTermDegs, thisLeadingTermC) = thisCoefficients.leadingTerm()
    val (otherLeadingTermDegs, otherLeadingTermC) = otherCoefficients.leadingTerm()

    while (
        thisLeadingTermDegs.size >= otherLeadingTermDegs.size &&
        (0..otherLeadingTermDegs.lastIndex).all { thisLeadingTermDegs[it] >= otherLeadingTermDegs[it] }
    ) {
        val multiplierDegs =
            thisLeadingTermDegs
                .mapIndexed { index, deg -> deg - otherLeadingTermDegs.getOrElse(index) { 0 } }
                .cleanUp()
        val multiplierC = thisLeadingTermC / otherLeadingTermC

        quotientCoefficients[multiplierDegs] = multiplierC

        for ((degs, t) in otherCoefficients) {
            val productDegs =
                (0..max(degs.lastIndex, multiplierDegs.lastIndex))
                    .map { degs.getOrElse(it) { 0 } + multiplierDegs.getOrElse(it) { 0 } }
                    .cleanUp()
            val productC = t * multiplierC
            thisCoefficients[productDegs] =
                if (productDegs in thisCoefficients) thisCoefficients[productDegs]!! - productC else -productC
        }

        thisCoefficients = thisCoefficients.filterValues { it.isNotZero() }.toMutableMap()

        if (thisCoefficients.isEmpty())
            return Polynomial.Companion.DividingResult(
                Polynomial(quotientCoefficients, toCheckInput = false),
                Polynomial(thisCoefficients, toCheckInput = false)
            )

        val t = thisCoefficients.leadingTerm()
        thisLeadingTermDegs = t.first
        thisLeadingTermC = t.second
    }

    return Polynomial.Companion.DividingResult(
        Polynomial(quotientCoefficients, toCheckInput = false),
        Polynomial(thisCoefficients, toCheckInput = false)
    )
}

// endregion

// region Constants

operator fun <T: Ring<T>> T.plus(other: Polynomial<T>) = other + this
operator fun <T: Ring<T>> Integer.plus(other: Polynomial<T>) = other + this
operator fun <T: Ring<T>> Int.plus(other: Polynomial<T>) = other + this
operator fun <T: Ring<T>> Long.plus(other: Polynomial<T>) = other + this

operator fun <T: Ring<T>> T.minus(other: Polynomial<T>) = -other + this
operator fun <T: Ring<T>> Integer.minus(other: Polynomial<T>) = -other + this
operator fun <T: Ring<T>> Int.minus(other: Polynomial<T>) = -other + this
operator fun <T: Ring<T>> Long.minus(other: Polynomial<T>) = -other + this

operator fun <T: Ring<T>> T.times(other: Polynomial<T>) = other * this
operator fun <T: Ring<T>> Integer.times(other: Polynomial<T>) = other * this
operator fun <T: Ring<T>> Int.times(other: Polynomial<T>) = other * this
operator fun <T: Ring<T>> Long.times(other: Polynomial<T>) = other * this

// endregion

// region Polynomials

operator fun <T: Ring<T>> Polynomial<T>.plus(other: UnivariatePolynomial<T>) = this + other.toPolynomial()
operator fun <T: Ring<T>> Polynomial<T>.plus(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        this * other.denominator.toPolynomial() + other.numerator.toPolynomial(),
        other.denominator.toPolynomial()
    )
operator fun <T: Ring<T>> Polynomial<T>.plus(other: RationalFunction<T>) =
    RationalFunction(
        this * other.denominator + other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> Polynomial<T>.plus(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() + other
operator fun <T: Ring<T>> Polynomial<T>.plus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.denominator + other.numerator,
        other.denominator
    )

operator fun <T: Ring<T>> Polynomial<T>.minus(other: UnivariatePolynomial<T>) = this - other.toPolynomial()
operator fun <T: Ring<T>> Polynomial<T>.minus(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        this * other.denominator.toPolynomial() - other.numerator.toPolynomial(),
        other.denominator.toPolynomial()
    )
operator fun <T: Ring<T>> Polynomial<T>.minus(other: RationalFunction<T>) =
    RationalFunction(
        this * other.denominator - other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> Polynomial<T>.minus(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() - other
operator fun <T: Ring<T>> Polynomial<T>.minus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.denominator - other.numerator,
        other.denominator
    )

operator fun <T: Ring<T>> Polynomial<T>.times(other: UnivariatePolynomial<T>) = this * other.toPolynomial()
operator fun <T: Ring<T>> Polynomial<T>.times(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        this * other.numerator.toPolynomial(),
        other.denominator.toPolynomial()
    )
operator fun <T: Ring<T>> Polynomial<T>.times(other: RationalFunction<T>) =
    RationalFunction(
        this * other.numerator,
        other.denominator
    )
operator fun <T: Ring<T>> Polynomial<T>.times(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() * other
operator fun <T: Ring<T>> Polynomial<T>.times(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.numerator,
        other.denominator
    )

operator fun <T: Field<T>> Polynomial<T>.div(other: UnivariatePolynomial<T>) = this / other.toPolynomial()
operator fun <T: Ring<T>> Polynomial<T>.div(other: UnivariateRationalFunction<T>) =
    RationalFunction(
        this * other.denominator.toPolynomial(),
        other.numerator.toPolynomial()
    )
operator fun <T: Ring<T>> Polynomial<T>.div(other: RationalFunction<T>) =
    RationalFunction(
        this * other.denominator,
        other.numerator
    )
operator fun <T: Field<T>> Polynomial<T>.div(other: LabeledPolynomial<T>) = this.toLabeledPolynomial() / other
operator fun <T: Ring<T>> Polynomial<T>.div(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this.toLabeledPolynomial() * other.denominator,
        other.numerator
    )

// endregion

// endregion

// region Invocation
/**
 * Returns numerator (polynomial) of rational function gotten by substitution rational functions in [arg] to the polynomial instance.
 * More concrete, if [arg] is mapping `x_1` to `f_1/g_1`, `x_2` to `f_2/g_2` etc., and the receiving instance is `p(x_1, x_2, ...)`, then
 * ```
 * p(f_1/g_1, f_2/g_2, ...) * g_1^deg_{x_1}(p) * g_2^deg_{x_2}(p) * ...
 * ```
 * is returned.
 *
 * If [arg] mapping extra variable not used in `p`, then they are ignored.
 *
 * Used in [Polynomial.invoke] and [RationalFunction.invoke] for performance optimisation.
 */
internal infix fun <T: Ring<T>> Polynomial<T>.invokeRFTakeNumerator (arg: Map<Int, RationalFunction<T>>): Polynomial<T> =
    if (isZero()) this
    else
        coefficients
            .asSequence()
            .map { (degs, c) ->
                (0 until countOfVariables).fold(
                    Polynomial(
                        degs.mapIndexed { index, deg -> if (index in arg) 0 else deg } to c
                    )
                ) { acc, index -> if (index in arg)
                    multiplyByPower(
                        multiplyByPower(acc, arg[index]!!.numerator, degs.getOrElse(index) { 0 }),
                        arg[index]!!.denominator,
                        degrees[index] - degs.getOrElse(index) { 0 }
                    ) else acc }
            }
            .reduce { acc, polynomial -> acc + polynomial } // TODO: Rewrite. Might be slow.
// endregion

// region Greatest Common Divisor (GCD) computation
/**
 * Represent the multivariate polynomial [this] as a univariate polynomial of selected variable [index]
 * with coefficients of other variables.
 *
 * For example polynomial
 * ```
 * x_1^2 + x_2^2 + x_3^2 + x_1 x_2 + x_2 x_3 + x_3 x_1
 * ```
 * will be represented with [index] = 2 as
 * ```
 * (x^1^2 + x^3^2 + x^3 x^1) x_2^0 + (x_1 + x_3) x_2^1 + (1) x^2^2
 * ```
 *
 * @receiver Represented polynomial
 * @param index Index of variable generated polynomial is of.
 * @return Described univariate polynomial with multivariate polynomial coefficients.
 */
internal fun <T: Field<T>> Polynomial<T>.markOutVariableCoefficients(index: Int): List<Polynomial<T>> {
    if (countOfVariables <= index) return listOf(this)

    val coefs = List(degrees[index] + 1) { emptyMap<List<Int>, T>().toMutableMap() }

    for ((degs, t) in coefficients) {
        coefs[
                degs.getOrElse(index) { 0 }
        ][
                degs
                    .toMutableList()
                    .apply {
                        when {
                            index < lastIndex -> this[index] = 0
                            index == lastIndex -> {
                                this.removeAt(lastIndex)
                            }
                        }
                    }
                    .cleanUp()
        ] = t
    }
    return coefs
        .map {
            Polynomial(
                it.ifEmpty { mapOf(emptyList<Int>() to this.ringZero) },
                toCheckInput = false
            )
        }
}

internal fun <T: Field<T>> Polynomial<T>.markOutVariable(index: Int): UnivariatePolynomial<RationalFunction<T>> =
    UnivariatePolynomial( this.markOutVariableCoefficients(index).map { it.toRationalFunction() } )

internal fun <T: Field<T>> UnivariatePolynomial<RationalFunction<T>>.markInVariable(index: Int): Polynomial<T> {
    var coefs = this.coefficients.toMutableList()
    for (i in coefs.indices) {
        val currentRatFun =  coefs[i].reduced()
        coefs = coefs.map { it * currentRatFun.denominator }.toMutableList()
        coefs[i] = RationalFunction(currentRatFun.numerator)
    }

    var coefs2 = coefs.map { it.numerator }
    val globalCoefGCD = polynomialGCD(coefs2)
    coefs2 = coefs2.map { it / globalCoefGCD }

    val newCoefs = mutableMapOf<List<Int>, T>()
    for ((deg, pol) in coefs2.withIndex()) for ((degs, t) in pol.coefficients) {
        newCoefs[
                degs
                    .toMutableList()
                    .apply {
                        if (size > index) throw Error("Index of variable with greatest index is not greatest")
                        if (deg != 0) addAll(List(index - size) { 0 } + listOf(deg))
                    }
        ] = t
    }
    return Polynomial(newCoefs, toCheckInput = false)
}

fun <T: Field<T>> polynomialBinGCD(P: Polynomial<T>, Q: Polynomial<T>): Polynomial<T> {
    if (P.isZero()) return Q
    if (Q.isZero()) return P

    if (P.isConstant()) return P
    if (Q.isConstant()) return Q

    val variable = max(P.countOfVariables, Q.countOfVariables) - 1
    val GCDByTheVariable = polynomialBinGCD(P.markOutVariable(variable), Q.markOutVariable(variable)).markInVariable(variable)
    val PRest = (P / GCDByTheVariable).markOutVariableCoefficients(variable)
    val QRest = (Q / GCDByTheVariable).markOutVariableCoefficients(variable)
    return polynomialGCD(PRest + QRest) * GCDByTheVariable
}

fun <T: Field<T>> polynomialGCD(pols: List<Polynomial<T>>): Polynomial<T> = pols.reduce(::polynomialBinGCD)
fun <T: Field<T>> polynomialGCD(vararg pols: Polynomial<T>): Polynomial<T> = polynomialGCD(pols.toList())
// endregion