package math.polynomials

import math.polynomials.LabeledPolynomial.Companion.cleanUp
import math.ringsAndFields.*


/**
 * Converts the value [this] over ring [T] as polynomial over ring [T].
 */
fun <T: Ring<T>> T.toLabeledPolynomial() = LabeledPolynomial(mapOf<Variable, Int>() to this, toCheckInput = false)

// region Operator extensions

// region Field case
/**
 *
 */
operator fun <T: Field<T>> LabeledPolynomial<T>.div(other: LabeledPolynomial<T>): LabeledPolynomial<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")
    if (isZero()) return this

    val commonVariables = (variables union other.variables).sorted()

    fun Map<Map<Variable, Int>, T>.leadingTerm() =
        this
            .asSequence()
            .map { Pair(it.key, it.value) }
            .reduce { (accDegs, accC), (listDegs, listC) ->
                for (variable in commonVariables) {
                    if (accDegs.getOrDefault(variable, 0) > listDegs.getOrDefault(variable, 0)) return@reduce accDegs to accC
                    if (accDegs.getOrDefault(variable, 0) < listDegs.getOrDefault(variable, 0)) return@reduce listDegs to listC
                }
                accDegs to accC
            }

    var thisCoefficients = coefficients.toMutableMap()
    val otherCoefficients = other.coefficients
    val quotientCoefficients = HashMap<Map<Variable, Int>, T>()

    var (thisLeadingTermDegs, thisLeadingTermC) = thisCoefficients.leadingTerm()
    val (otherLeadingTermDegs, otherLeadingTermC) = otherCoefficients.leadingTerm()

    while (
        thisLeadingTermDegs.size >= otherLeadingTermDegs.size &&
        commonVariables.all { thisLeadingTermDegs.getOrDefault(it, 0) >= otherLeadingTermDegs.getOrDefault(it, 0) }
    ) {
        val multiplierDegs =
            thisLeadingTermDegs
                .mapValues { (variable, deg) -> deg - otherLeadingTermDegs.getOrElse(variable) { 0 } }
                .cleanUp()
        val multiplierC = thisLeadingTermC / otherLeadingTermC

        quotientCoefficients[multiplierDegs] = multiplierC

        for ((degs, t) in otherCoefficients) {
            val productDegs =
                (degs.keys + multiplierDegs.keys)
                    .associateWith { degs.getOrDefault(it, 0) + multiplierDegs.getOrDefault(it, 0) }
            val productC = t * multiplierC
            thisCoefficients[productDegs] =
                if (productDegs in thisCoefficients) thisCoefficients[productDegs]!! - productC else -productC
        }

        thisCoefficients = thisCoefficients.filterValues { it.isNotZero() }.toMutableMap()

        if (thisCoefficients.isEmpty())
            return LabeledPolynomial(quotientCoefficients, toCheckInput = false)

        val t = thisCoefficients.leadingTerm()
        thisLeadingTermDegs = t.first
        thisLeadingTermC = t.second
    }

    return LabeledPolynomial(quotientCoefficients, toCheckInput = false)
}

operator fun <T: Field<T>> LabeledPolynomial<T>.div(other: T): LabeledPolynomial<T> =
    if (other.isZero()) throw ArithmeticException("/ by zero")
    else
        LabeledPolynomial(
            coefficients
                .mapValues { it.value / other },
            toCheckInput = false
        )

operator fun <T: Field<T>> LabeledPolynomial<T>.rem(other: LabeledPolynomial<T>): LabeledPolynomial<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")
    if (isZero()) return this

    val commonVariables = (variables union other.variables).sorted()

    fun Map<Map<Variable, Int>, T>.leadingTerm() =
        this
            .asSequence()
            .map { Pair(it.key, it.value) }
            .reduce { (accDegs, accC), (listDegs, listC) ->
                for (variable in commonVariables) {
                    if (accDegs.getOrDefault(variable, 0) > listDegs.getOrDefault(variable, 0)) return@reduce accDegs to accC
                    if (accDegs.getOrDefault(variable, 0) < listDegs.getOrDefault(variable, 0)) return@reduce listDegs to listC
                }
                accDegs to accC
            }

    var thisCoefficients = coefficients.toMutableMap()
    val otherCoefficients = other.coefficients

    var (thisLeadingTermDegs, thisLeadingTermC) = thisCoefficients.leadingTerm()
    val (otherLeadingTermDegs, otherLeadingTermC) = otherCoefficients.leadingTerm()

    while (
        thisLeadingTermDegs.size >= otherLeadingTermDegs.size &&
        commonVariables.all { thisLeadingTermDegs.getOrDefault(it, 0) >= otherLeadingTermDegs.getOrDefault(it, 0) }
    ) {
        val multiplierDegs =
            thisLeadingTermDegs
                .mapValues { (variable, deg) -> deg - otherLeadingTermDegs.getOrElse(variable) { 0 } }
                .cleanUp()
        val multiplierC = thisLeadingTermC / otherLeadingTermC

        for ((degs, t) in otherCoefficients) {
            val productDegs =
                (degs.keys + multiplierDegs.keys)
                    .associateWith { degs.getOrDefault(it, 0) + multiplierDegs.getOrDefault(it, 0) }
            val productC = t * multiplierC
            thisCoefficients[productDegs] =
                if (productDegs in thisCoefficients) thisCoefficients[productDegs]!! - productC else -productC
        }

        thisCoefficients = thisCoefficients.filterValues { it.isNotZero() }.toMutableMap()

        if (thisCoefficients.isEmpty())
            return LabeledPolynomial(thisCoefficients, toCheckInput = false)

        val t = thisCoefficients.leadingTerm()
        thisLeadingTermDegs = t.first
        thisLeadingTermC = t.second
    }

    return LabeledPolynomial(thisCoefficients, toCheckInput = false)
}

infix fun <T: Field<T>> LabeledPolynomial<T>.divrem(other: LabeledPolynomial<T>): LabeledPolynomial.Companion.DividingResult<T> {
    if (other.isZero()) throw ArithmeticException("/ by zero")
    if (isZero()) return LabeledPolynomial.Companion.DividingResult(this, this)

    val commonVariables = (variables union other.variables).sorted()

    fun Map<Map<Variable, Int>, T>.leadingTerm() =
        this
            .asSequence()
            .map { Pair(it.key, it.value) }
            .reduce { (accDegs, accC), (listDegs, listC) ->
                for (variable in commonVariables) {
                    if (accDegs.getOrDefault(variable, 0) > listDegs.getOrDefault(variable, 0)) return@reduce accDegs to accC
                    if (accDegs.getOrDefault(variable, 0) < listDegs.getOrDefault(variable, 0)) return@reduce listDegs to listC
                }
                accDegs to accC
            }

    var thisCoefficients = coefficients.toMutableMap()
    val otherCoefficients = other.coefficients
    val quotientCoefficients = HashMap<Map<Variable, Int>, T>()

    var (thisLeadingTermDegs, thisLeadingTermC) = thisCoefficients.leadingTerm()
    val (otherLeadingTermDegs, otherLeadingTermC) = otherCoefficients.leadingTerm()

    while (
        thisLeadingTermDegs.size >= otherLeadingTermDegs.size &&
        commonVariables.all { thisLeadingTermDegs.getOrDefault(it, 0) >= otherLeadingTermDegs.getOrDefault(it, 0) }
    ) {
        val multiplierDegs =
            thisLeadingTermDegs
                .mapValues { (variable, deg) -> deg - otherLeadingTermDegs.getOrElse(variable) { 0 } }
                .cleanUp()
        val multiplierC = thisLeadingTermC / otherLeadingTermC

        quotientCoefficients[multiplierDegs] = multiplierC

        for ((degs, t) in otherCoefficients) {
            val productDegs =
                (degs.keys + multiplierDegs.keys)
                    .associateWith { degs.getOrDefault(it, 0) + multiplierDegs.getOrDefault(it, 0) }
            val productC = t * multiplierC
            thisCoefficients[productDegs] =
                if (productDegs in thisCoefficients) thisCoefficients[productDegs]!! - productC else -productC
        }

        thisCoefficients = thisCoefficients.filterValues { it.isNotZero() }.toMutableMap()

        if (thisCoefficients.isEmpty())
            return LabeledPolynomial.Companion.DividingResult(
                LabeledPolynomial(quotientCoefficients, toCheckInput = false),
                LabeledPolynomial(thisCoefficients, toCheckInput = false)
            )

        val t = thisCoefficients.leadingTerm()
        thisLeadingTermDegs = t.first
        thisLeadingTermC = t.second
    }

    return LabeledPolynomial.Companion.DividingResult(
        LabeledPolynomial(quotientCoefficients, toCheckInput = false),
        LabeledPolynomial(thisCoefficients, toCheckInput = false)
    )
}

// endregion

// region Constants

operator fun <T: Ring<T>> T.plus(other: LabeledPolynomial<T>) = other + this
operator fun <T: Ring<T>> Integer.plus(other: LabeledPolynomial<T>) = other + this
operator fun <T: Ring<T>> Int.plus(other: LabeledPolynomial<T>) = other + this
operator fun <T: Ring<T>> Long.plus(other: LabeledPolynomial<T>) = other + this

operator fun <T: Ring<T>> T.minus(other: LabeledPolynomial<T>) = -other + this
operator fun <T: Ring<T>> Integer.minus(other: LabeledPolynomial<T>) = -other + this
operator fun <T: Ring<T>> Int.minus(other: LabeledPolynomial<T>) = -other + this
operator fun <T: Ring<T>> Long.minus(other: LabeledPolynomial<T>) = -other + this

operator fun <T: Ring<T>> T.times(other: LabeledPolynomial<T>) = other * this
operator fun <T: Ring<T>> Integer.times(other: LabeledPolynomial<T>) = other * this
operator fun <T: Ring<T>> Int.times(other: LabeledPolynomial<T>) = other * this
operator fun <T: Ring<T>> Long.times(other: LabeledPolynomial<T>) = other * this

// endregion

// region Polynomials

operator fun <T: Ring<T>> LabeledPolynomial<T>.plus(other: UnivariatePolynomial<T>) = this + other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.plus(other: UnivariateRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator.toLabeledPolynomial() + other.numerator.toLabeledPolynomial(),
        other.denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> LabeledPolynomial<T>.plus(other: Polynomial<T>) = this + other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.plus(other: RationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator.toLabeledPolynomial() + other.numerator.toLabeledPolynomial(),
        other.denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> LabeledPolynomial<T>.plus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator + other.numerator,
        other.denominator
    )

operator fun <T: Ring<T>> LabeledPolynomial<T>.minus(other: UnivariatePolynomial<T>) = this - other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.minus(other: UnivariateRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator.toLabeledPolynomial() - other.numerator.toLabeledPolynomial(),
        other.denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> LabeledPolynomial<T>.minus(other: Polynomial<T>) = this - other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.minus(other: RationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator.toLabeledPolynomial() - other.numerator.toLabeledPolynomial(),
        other.denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> LabeledPolynomial<T>.minus(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator - other.numerator,
        other.denominator
    )

operator fun <T: Ring<T>> LabeledPolynomial<T>.times(other: UnivariatePolynomial<T>) = this * other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.times(other: UnivariateRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.numerator.toLabeledPolynomial(),
        other.denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> LabeledPolynomial<T>.times(other: Polynomial<T>) = this * other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.times(other: RationalFunction<T>) =
    LabeledRationalFunction(
        this * other.numerator.toLabeledPolynomial(),
        other.denominator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> LabeledPolynomial<T>.times(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.numerator,
        other.denominator
    )

operator fun <T: Field<T>> LabeledPolynomial<T>.div(other: UnivariatePolynomial<T>) = this / other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.div(other: UnivariateRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator.toLabeledPolynomial(),
        other.numerator.toLabeledPolynomial()
    )
operator fun <T: Field<T>> LabeledPolynomial<T>.div(other: Polynomial<T>) = this / other.toLabeledPolynomial()
operator fun <T: Ring<T>> LabeledPolynomial<T>.div(other: RationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator.toLabeledPolynomial(),
        other.numerator.toLabeledPolynomial()
    )
operator fun <T: Ring<T>> LabeledPolynomial<T>.div(other: LabeledRationalFunction<T>) =
    LabeledRationalFunction(
        this * other.denominator,
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
 * Used in [LabeledPolynomial.invoke] and [LabeledRationalFunction.invoke] for performance optimisation.
 */
internal infix fun <T: Ring<T>> LabeledPolynomial<T>.invokeRFTakeNumerator (arg: Map<Variable, LabeledRationalFunction<T>>): LabeledPolynomial<T> =
    if (isZero()) this
    else {
        val arg = arg.filterKeys { it in degrees }
        coefficients
            .asSequence()
            .map { (degs, c) ->
                arg.entries.fold(
                    LabeledPolynomial(
                        degs.filterKeys { it !in arg } to c
                    )
                ) { acc, (variable, substitution) ->
                    multiplyByPower(
                        multiplyByPower(acc, substitution.numerator, degs.getOrDefault(variable, 0)),
                        substitution.denominator,
                        degrees[variable]!! - degs.getOrDefault(variable, 0)
                    )
                }
            }
            .reduce { acc, polynomial -> acc + polynomial } // TODO: Rewrite. Might be slow.
    }
// endregion

// region Greatest Common Divisor (GCD) computation
internal fun <T: Field<T>> LabeledPolynomial<T>.markOutVariableCoefficients(variable: Variable): List<LabeledPolynomial<T>> {
    if (variable !in variables) return listOf(this)

    val coefs = List(degrees[variable]!! + 1) { emptyMap<Map<Variable, Int>, T>().toMutableMap() }

    for ((degs, t) in coefficients) {
        coefs[
                degs.getOrDefault(variable, 0)
        ][
                degs
                    .toMutableMap()
                    .apply {
                        this.remove(variable)
                    }
        ] = t
    }
    return coefs
        .map {
            LabeledPolynomial(
                it.ifEmpty { mapOf(emptyMap<Variable, Int>() to this.ringZero) },
                toCheckInput = false
            )
        }
}

internal fun <T: Field<T>> LabeledPolynomial<T>.markOutVariable(variable: Variable): UnivariatePolynomial<LabeledRationalFunction<T>> =
    UnivariatePolynomial( this.markOutVariableCoefficients(variable).map { it.toLabeledRationalFunction() } )

internal fun <T: Field<T>> UnivariatePolynomial<LabeledRationalFunction<T>>.markInVariable(variable: Variable): LabeledPolynomial<T> {
    var coefs = this.coefficients.toMutableList()
    for (i in coefs.indices) {
        val currentRatFun =  coefs[i].reduced()
        coefs = coefs.map { it * currentRatFun.denominator }.toMutableList()
        coefs[i] = LabeledRationalFunction(currentRatFun.numerator)
    }

    var coefs2 = coefs.map { it.numerator }
    val globalCoefGCD = polynomialGCD(coefs2)
    coefs2 = coefs2.map { it / globalCoefGCD }

    val newCoefs = mutableMapOf<Map<Variable, Int>, T>()
    for ((deg, pol) in coefs2.withIndex()) for ((degs, t) in pol.coefficients) {
        newCoefs[
                degs
                    .toMutableMap()
                    .apply {
                        if (variable in this) throw Error("Met eliminated variable")
                        if (deg != 0) this[variable] = deg
                    }
        ] = t
    }
    return LabeledPolynomial(newCoefs, toCheckInput = false)
}

/**
 * Returns GCD (greatest common divider) of the polynomials [P] and [Q].
 *
 * Special cases:
 * - If [P] is zero polynomial [Q] is returned.
 * - If [Q] is zero polynomial [P] is returned.
 * - If both [P] and [Q] are zero polynomials zero polynomial is returned.
 *
 * @param T Field where we are working now.
 * @return GCD of the polynomials.
 */
fun <T: Field<T>> polynomialBinGCD(P: LabeledPolynomial<T>, Q: LabeledPolynomial<T>): LabeledPolynomial<T> {
    println(P)
    println(Q)
    println()
    if (P.isZero()) return Q
    if (Q.isZero()) return P

    if (P.isConstant()) return P
    if (Q.isConstant()) return Q

    val variable = (P.variables union Q.variables).maxByOrNull { it.name }!!
    val GCDByTheVariable = polynomialBinGCD(P.markOutVariable(variable), Q.markOutVariable(variable)).markInVariable(variable)
    val PRest = (P / GCDByTheVariable).markOutVariableCoefficients(variable)
    val QRest = (Q / GCDByTheVariable).markOutVariableCoefficients(variable)
    return polynomialGCD(PRest + QRest) * GCDByTheVariable
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
fun <T: Field<T>> polynomialGCD(pols: List<LabeledPolynomial<T>>): LabeledPolynomial<T> = pols.reduce(::polynomialBinGCD)
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
fun <T: Field<T>> polynomialGCD(vararg pols: LabeledPolynomial<T>): LabeledPolynomial<T> = polynomialGCD(pols.toList())
// endregion