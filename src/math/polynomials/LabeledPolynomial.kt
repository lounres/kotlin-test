package math.polynomials

import math.ringsAndFields.*
import kotlin.math.max


/**
 * Represents multivariate polynomials with labeled variables..
 *
 * @param T Ring in which the polynomial is considered.
 * @param coefs Coefficients of the instants.
 * @param toCheckInput If it's `true` cleaning of [coefficients] is executed otherwise it is not.
 *
 * @constructor Gets the coefficients in format of [coefficients] field and cleans it: removes zero degrees from keys of
 * received map, sums up proportional monomials, removes aero monomials, and if result is zero map adds only element in
 * it.
 *
 * @throws LabeledPolynomialError If no coefficient received or if any of degrees in any monomial is negative.
 */
class LabeledPolynomial<T: Ring<T>>
internal constructor(
    coefs: Map<Map<Variable, Int>, T>,
    toCheckInput: Boolean = true
) : Ring<LabeledPolynomial<T>> {
    /**
     * Map that collects coefficients of the polynomial. Every non-zero monomial
     * `a x_1^{d_1} ... x_n^{d_n}` is represented as pair "key-value" in the map, where value is coefficients `a` and
     * key is map that associates variables in the monomial with multiplicity of them occurring in the monomial.
     * For example polynomial
     * ```
     * 5 a^2 c^3 - 6 b + 0 bc
     * ```
     * has coefficients represented as
     * ```
     * mapOf(
     *      mapOf(
     *          a to 2,
     *          c to 3
     *      ) to 5,
     *      mapOf(
     *          b to 1
     *      ) to (-6)
     * )
     * ```
     * where `a`, `b` and `c` are corresponding [Variable] objects.
     *
     * There is only one special case: if the polynomial is zero, is contains only one monomial:
     * ```
     * mapOf<Variable, Int>() to ZERO
     * ```
     * where `ZERO` is the ring zero.
     */
    val coefficients: Map<Map<Variable, Int>, T>
    /**
     * Signaling if the instance is zero polynomial and as consequence if it is special case.
     *
     * To check for equality to the zero polynomial use [isZero].
     */
    private val isZero: Boolean

    init {
        if (toCheckInput) {
            if (coefs.isEmpty()) throw LabeledPolynomialError("Polynomial must contain at least one (maybe zero) monomial")
            if (coefs.keys.any { it.values.any { it < 0 } }) throw LabeledPolynomialError("All monomials should contain only non-negative degrees")

            // Temporary ringExemplar. Used to get zero or one of the ring.
            val ringExemplar = coefs.entries.first().value
            // Map for cleaned coefficients.
            val fixedCoefs = mutableMapOf<Map<Variable, Int>, T>()

            // Cleaning the degrees, summing monomials of the same degrees.
            for (entry in coefs) {
                val key = entry.key.cleanUp()
                val value = entry.value
                fixedCoefs[key] = if (key in fixedCoefs) fixedCoefs[key]!! + value else value
            }

            // Removing zero monomials.
            coefficients = fixedCoefs
                .filter { it.value.isNotZero() }
                .let {
                    if (it.isNotEmpty()) {
                        isZero = false
                        it
                    } else {
                        isZero = true
                        mapOf(emptyMap<Variable, Int>() to ringExemplar.getZero())
                    }
                }
        } else {
            coefficients = coefs
            isZero = (coefs.all { it.value.isZero() })
        }
    }

    /**
     * Degree of the polynomial, [see also](https://en.wikipedia.org/wiki/Degree_of_a_polynomial). If the polynomial is
     * zero, degree is -1.
     */
    val degree: Int by lazy { if (isZero) -1 else coefficients.keys.maxOf { it.values.sum() } }
    /**
     * Map that associates variables (that appear in the polynomial in positive exponents) with their most exponents
     * in which they are appeared in the polynomial.
     *
     * As consequence all values in the map are positive integers. Also if the polynomial is constant, the map is empty.
     * And keys of the map is the same as in [variables].
     */
    val degrees: Map<Variable, Int> by lazy { if (isZero) emptyMap<Variable, Int>() else
        coefficients.keys.fold(mutableMapOf()) { acc, map ->
            map.mapValuesTo(acc) { (variable, deg) -> max(acc.getOrDefault(variable, 0), deg) }
        }
    }
    /**
     * Set of all variables that appear in the polynomial in positive exponents.
     */
    val variables: Set<Variable> by lazy { degrees.keys }
    /**
     * Count of all variables that appear in the polynomial in positive exponents.
     */
    val countOfVariables: Int by lazy { variables.size }

    /**
     * Simple way to access any exemplar of the ring. Used to get [ringZero] and [ringOne] &ndash; zero and one of the ring.
     */
    internal val ringExemplar: T get() = coefficients.entries.first().value
    /**
     * Simple way to access the zero of the ring.
     */
    internal val ringZero: T get() = ringExemplar.getZero()
    /**
     * Simple way to access the one of the ring.
     */
    internal val ringOne: T get() = ringExemplar.getOne()

    /**
     * Gets the coefficients in format of [coefficients] field and cleans it: removes zero degrees from keys of received
     * map, sums up proportional monomials, removes aero monomials, and if result is zero map adds only element in it.
     *
     * @param pairs Collection of pairs that represents monomials.
     * @param toCheckInput If it's `true` cleaning of [coefficients] is executed otherwise it is not.
     *
     * @throws LabeledPolynomialError If no coefficient received or if any of degrees in any monomial is negative.
     */
    internal constructor(pairs: Collection<Pair<Map<Variable, Int>, T>>, toCheckInput: Boolean) : this(
        if (toCheckInput) {
            if (pairs.isEmpty()) throw LabeledPolynomialError("Polynomial must contain at least one (maybe zero) monomial")
            if (pairs.any { it.first.values.any { it < 0 } }) throw LabeledPolynomialError("All monomials should contain only non-negative degrees")

            // Temporary ringExemplar. Used to get zero or one of the ring.
            val ringExemplar = pairs.first().second
            // Map for cleaned coefficients.
            val fixedCoefs = mutableMapOf<Map<Variable, Int>, T>()

            // Cleaning the degrees, summing monomials of the same degrees.
            for (entry in pairs) {
                val key = entry.first.cleanUp()
                val value = entry.second
                fixedCoefs[key] = if (key in fixedCoefs) fixedCoefs[key]!! + value else value
            }

            // Removing zero monomials.
            fixedCoefs
                .filter { it.value.isNotZero() }
                .ifEmpty {
                    mapOf(emptyMap<Variable, Int>() to ringExemplar.getZero())
                }
        } else pairs.toMap(),
        toCheckInput = false
    )
    /**
     * Gets the coefficients in format of [coefficients] field and cleans it: removes zero degrees from keys of received
     * map, sums up proportional monomials, removes aero monomials, and if result is zero map adds only element in it.
     *
     * @param pairs Collection of pairs that represents monomials.
     * @param toCheckInput If it's `true` cleaning of [coefficients] is executed otherwise it is not.
     *
     * @throws LabeledPolynomialError If no coefficient received or if any of degrees in any monomial is negative.
     */
    internal constructor(vararg pairs: Pair<Map<Variable, Int>, T>, toCheckInput: Boolean) : this(pairs.toList(), toCheckInput)
    /**
     * Gets the coefficients in format of [coefficients] field and cleans it: removes zero degrees from keys of received
     * map, sums up proportional monomials, removes aero monomials, and if result is zero map adds only element in it.
     *
     * @param coefs Coefficients of the instants.
     *
     * @throws LabeledPolynomialError If no coefficient received or if any of degrees in any monomial is negative.
     */
    constructor(coefs: Map<Map<Variable, Int>, T>) : this(coefs, toCheckInput = true)
    /**
     * Gets the coefficients in format of [coefficients] field and cleans it: removes zero degrees from keys of received
     * map, sums up proportional monomials, removes aero monomials, and if result is zero map adds only element in it.
     *
     * @param pairs Collection of pairs that represents monomials.
     *
     * @throws LabeledPolynomialError If no coefficient received or if any of degrees in any monomial is negative.
     */
    constructor(pairs: List<Pair<Map<Variable, Int>, T>>) : this(pairs, toCheckInput = true)
    /**
     * Gets the coefficients in format of [coefficients] field and cleans it: removes zero degrees from keys of received
     * map, sums up proportional monomials, removes aero monomials, and if result is zero map adds only element in it.
     *
     * @param pairs Collection of pairs that represents monomials.
     *
     * @throws LabeledPolynomialError If no coefficient received or if any of degrees in any monomial is negative.
     */
    constructor(vararg pairs: Pair<Map<Variable, Int>, T>) : this(*pairs, toCheckInput = true)

    /**
     * Returns the zero polynomial (additive identity) over considered ring.
     */
    override fun getZero(): LabeledPolynomial<T> = LabeledPolynomial(emptyMap<Variable, Int>() to ringZero, toCheckInput = false)
    /**
     * Returns the unit polynomial (multiplicative identity) over considered ring.
     */
    override fun getOne(): LabeledPolynomial<T> = LabeledPolynomial(emptyMap<Variable, Int>() to ringOne, toCheckInput = false)
    /**
     * Checks if the instant is the zero polynomial (additive identity) over considered ring.
     */
    override fun isZero(): Boolean = isZero
    /**
     * Checks if the instant is the unit polynomial (multiplicative identity) over considered ring.
     */
    override fun isOne(): Boolean =
        coefficients.size == 1 &&
                coefficients.entries.first().let { (key, value) -> key.isEmpty() && value.isOne() }

    /**
     * Checks if the instant is constant polynomial (of degree no more than 0) over considered ring.
     */
    fun isConstant(): Boolean =
        coefficients.size == 1 &&
                coefficients.entries.first().let { (key, _) -> key.isEmpty() }
    /**
     * Checks if the instant is **not** constant polynomial (of degree no more than 0) over considered ring.
     */
    fun isNotConstant(): Boolean = !isConstant()
    /**
     * Checks if the instant is constant non-zero polynomial (of degree no more than 0) over considered ring.
     */
    fun isNonZeroConstant(): Boolean =
        coefficients.size == 1 &&
                coefficients.entries.first().let { (key, value) -> key.isEmpty() && value.isNotZero() }
    /**
     * Checks if the instant is **not** constant non-zero polynomial (of degree no more than 0) over considered ring.
     */
    fun isNotNonZeroConstant(): Boolean = !isNonZeroConstant()

    /**
     * Returns the same polynomial.
     */
    override operator fun unaryPlus(): LabeledPolynomial<T> = this

    /**
     * Returns negation of the polynomial.
     */
    override operator fun unaryMinus(): LabeledPolynomial<T> =
        LabeledPolynomial(
            coefficients
                .mapValues { -it.value },
            toCheckInput = false
        )

    /**
     * Returns sum of the polynomials.
     */
    override operator fun plus(other: LabeledPolynomial<T>): LabeledPolynomial<T> =
        LabeledPolynomial(
            coefficients
                .toMutableMap()
                .apply {
                    other.coefficients
                        .mapValuesTo(this) { (key, value) -> if (key in this) this[key]!! + value else value }
                }
                .filterValues { it.isNotZero() }
                .ifEmpty { mapOf(emptyMap<Variable, Int>() to ringZero) },
            toCheckInput = false
        )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    operator fun plus(other: T): LabeledPolynomial<T> =
        if (other.isZero()) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! + other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun plus(other: Integer): LabeledPolynomial<T> =
        if (other.isZero()) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! + other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = ringOne * other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun plus(other: Int): LabeledPolynomial<T> =
        if (other == 0) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! + other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = ringOne * other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun plus(other: Long): LabeledPolynomial<T> =
        if (other == 0L) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! + other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = ringOne * other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns difference of the polynomials.
     */
    override operator fun minus(other: LabeledPolynomial<T>): LabeledPolynomial<T> =
        LabeledPolynomial(
            coefficients
                .toMutableMap()
                .apply {
                    other.coefficients
                        .mapValuesTo(this) { (key, value) -> if (key in this) this[key]!! - value else -value }
                }
                .filterValues { it.isNotZero() }
                .ifEmpty { mapOf(emptyMap<Variable, Int>() to ringZero) },
            toCheckInput = false
        )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    operator fun minus(other: T): LabeledPolynomial<T> =
        if (other.isZero()) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! - other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = -other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun minus(other: Integer): LabeledPolynomial<T> =
        if (other.isZero()) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! - other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = -ringOne * other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun minus(other: Int): LabeledPolynomial<T> =
        if (other == 0) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! - other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = -ringOne * other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun minus(other: Long): LabeledPolynomial<T> =
        if (other == 0L) this
        else
            LabeledPolynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyMap<Variable, Int>()
                        if (key in this) {
                            val res = this[key]!! - other
                            if (res.isZero() && size == 1) {
                                this[key] = res
                            } else {
                                this.remove(key)
                            }
                        } else {
                            this[key] = -ringOne * other
                        }
                    },
                toCheckInput = false
            )

    /**
     * Returns product of the polynomials.
     */
    override operator fun times(other: LabeledPolynomial<T>): LabeledPolynomial<T> =
        when {
            isZero() -> this
            other.isZero() -> other
            else ->
                LabeledPolynomial(
                    mutableMapOf<Map<Variable, Int>, T>()
                        .apply {
                            for ((degs1, c1) in coefficients) for ((degs2, c2) in other.coefficients) {
                                val degs = degs1.toMutableMap()
                                degs2.mapValuesTo(degs) { (variable, deg) -> degs.getOrDefault(variable, 0) + deg }
                                val c = c1 * c2
                                this[degs] = if (degs in this) this[degs]!! + c else c
                            }
                        }
                        .filterValues { it.isNotZero() }
                        .ifEmpty { mapOf(emptyMap<Variable, Int>() to ringZero) },
                    toCheckInput = false
                )
        }

    /**
     * Returns product of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    operator fun times(other: T): LabeledPolynomial<T> =
        if (other.isZero()) getZero()
        else
            LabeledPolynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyMap<Variable, Int>() to ringZero) },
                toCheckInput = false
            )

    /**
     * Returns product of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun times(other: Integer): LabeledPolynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            LabeledPolynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyMap<Variable, Int>() to ringZero) },
                toCheckInput = false
            )

    /**
     * Returns product of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun times(other: Int): LabeledPolynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            LabeledPolynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyMap<Variable, Int>() to ringZero) },
                toCheckInput = false
            )

    /**
     * Returns product of the polynomials. [other] is interpreted as [LabeledPolynomial].
     */
    override operator fun times(other: Long): LabeledPolynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            LabeledPolynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyMap<Variable, Int>() to ringZero) },
                toCheckInput = false
            )


    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> {
                other as LabeledPolynomial<*>

                val ringType = ringExemplar::class.java

                if (other.coefficients.values.all { ringType.isInstance(it) }) {
                    @Suppress("UNCHECKED_CAST")
                    other as LabeledPolynomial<T>

                    coefficients.size == other.coefficients.size &&
                            coefficients.all { (key, value) -> with(other.coefficients) { key in this && this[key] == value } }
                } else false
            }
        }

    override fun hashCode(): Int = javaClass.hashCode()

    /**
     * Returns polynomial that is got as result of substitution of values of [arg] instead of corresponding keys. All
     * variables of the polynomial that is not in [arg] keys are left unsubstituted.
     */
    operator fun invoke(arg: Map<Variable, T>): LabeledPolynomial<T> =
        LabeledPolynomial(
            coefficients
                .map { (degs, c) ->
                    degs.filterKeys { it !in arg } to
                            degs.entries.asSequence().filter { it.key in arg }.fold(c) { acc, (variable, deg) ->
                                multiplyByPower(acc, arg[variable]!!, deg)
                            }
                }
        )

    /**
     * Returns polynomial that is got as result of substitution of values of [arg] instead of corresponding keys. All
     * variables of the polynomial that is not in [arg] keys are left unsubstituted.
     */
    @JvmName("invokePolynomial")
    operator fun invoke(arg: Map<Variable, LabeledPolynomial<T>>): LabeledPolynomial<T> =
        coefficients
            .asSequence()
            .map { (degs, c) ->
                degs.entries.asSequence().filter { it.key in arg }.fold(
                    LabeledPolynomial(
                        degs.filterKeys { it !in arg } to c
                    )
                ) { acc, (index, deg) -> multiplyByPower(acc, arg[index]!!, deg) }
            }
            .reduce { acc, polynomial -> acc + polynomial } // TODO: Rewrite. Might be slow.

    /**
     * Returns polynomial that is got as result of substitution of values of [arg] instead of corresponding keys. All
     * variables of the polynomial that is not in [arg] keys are left unsubstituted.
     */
    @JvmName("invokeRationalFunction")
    operator fun invoke(arg: Map<Variable, LabeledRationalFunction<T>>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            this invokeRFTakeNumerator arg,
            variables
                .asSequence()
                .filter { it in arg }
                .map { power(arg[it]!!.denominator, degrees[it]!!) }
                .fold(getOne()) { acc, polynomial ->  acc * polynomial }
        )

    /**
     * Represents the polynomial as a [String]. Consider that monomials are sorted in lexicographic order.
     */
    override fun toString(): String = toString(emptyMap())

    /**
     * Represents the polynomial as a [String] with names of variables substituted with names from [names].
     * Consider that monomials are sorted in lexicographic order.
     */
    fun toString(names: Map<Variable, String>): String =
        coefficients.entries
            .sortedWith { o1, o2 -> monomialComparator.compare(o1.key, o2.key) }
            .asSequence()
            .map { (degs, t) ->
                if (degs.isEmpty()) "$t"
                else {
                    when {
                        t.isOne() -> ""
                        t == -t.getOne() -> "-"
                        else -> "$t "
                    } +
                            degs
                                .toSortedMap()
                                .filter { it.value > 0 }
                                .map { (variable, deg) ->
                                    val variableName = names.getOrDefault(variable, variable.toString())
                                    when (deg) {
                                        1 -> variableName
                                        else -> "$variableName^$deg"
                                    }
                                }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    /**
     * Represents the polynomial as a [String] naming variables by [namer].
     * Consider that monomials are sorted in lexicographic order.
     */
    fun toString(namer: (Variable) -> String): String =
        coefficients.entries
            .sortedWith { o1, o2 -> monomialComparator.compare(o1.key, o2.key) }
            .asSequence()
            .map { (degs, t) ->
                if (degs.isEmpty()) "$t"
                else {
                    when {
                        t.isOne() -> ""
                        t == -t.getOne() -> "-"
                        else -> "$t "
                    } +
                            degs
                                .toSortedMap()
                                .filter { it.value > 0 }
                                .map { (variable, deg) ->
                                    when (deg) {
                                        1 -> namer(variable)
                                        else -> "${namer(variable)}^$deg"
                                    }
                                }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    /**
     * Represents the polynomial as a [String] with names of variables substituted with names from [names] and with
     * brackets around the string if needed (i.e. when there are at least two addends in the representation).
     * Consider that monomials are sorted in lexicographic order.
     */
    fun toStringWithBrackets(names: Map<Variable, String>): String =
        with(toString(names)) { if (coefficients.count() == 1) this else "($this)" }

    /**
     * Represents the polynomial as a [String] naming variables by [namer] and with brackets around the string if needed
     * (i.e. when there are at least two addends in the representation).
     * Consider that monomials are sorted in lexicographic order.
     */
    fun toStringWithBrackets(namer: (Variable) -> String): String =
        with(toString(namer)) { if (coefficients.count() == 1) this else "($this)" }

    /**
     * Represents the polynomial as a [String] with names of variables substituted with names from [names].
     * Consider that monomials are sorted in **reversed** lexicographic order.
     */
    fun toReversedString(names: Map<Variable, String>): String =
        coefficients.entries
            .sortedWith { o1, o2 -> -monomialComparator.compare(o1.key, o2.key) }
            .asSequence()
            .map { (degs, t) ->
                if (degs.isEmpty()) "$t"
                else {
                    when {
                        t.isOne() -> ""
                        t == -t.getOne() -> "-"
                        else -> "$t "
                    } +
                            degs
                                .toSortedMap()
                                .filter { it.value > 0 }
                                .map { (variable, deg) ->
                                    val variableName = names.getOrDefault(variable, variable.toString())
                                    when (deg) {
                                        1 -> variableName
                                        else -> "$variableName^$deg"
                                    }
                                }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    /**
     * Represents the polynomial as a [String] naming variables by [namer].
     * Consider that monomials are sorted in **reversed** lexicographic order.
     */
    fun toReversedString(namer: (Variable) -> String): String =
        coefficients.entries
            .sortedWith { o1, o2 -> -monomialComparator.compare(o1.key, o2.key) }
            .asSequence()
            .map { (degs, t) ->
                if (degs.isEmpty()) "$t"
                else {
                    when {
                        t.isOne() -> ""
                        t == -t.getOne() -> "-"
                        else -> "$t "
                    } +
                            degs
                                .toSortedMap()
                                .filter { it.value > 0 }
                                .map { (variable, deg) ->
                                    when (deg) {
                                        1 -> namer(variable)
                                        else -> "${namer(variable)}^$deg"
                                    }
                                }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    /**
     * Represents the polynomial as a [String] with names of variables substituted with names from [names] and with
     * brackets around the string if needed (i.e. when there are at least two addends in the representation).
     * Consider that monomials are sorted in **reversed** lexicographic order.
     */
    fun toReversedStringWithBrackets(names: Map<Variable, String>): String =
        with(toReversedString(names)) { if (coefficients.count() == 1) this else "($this)" }

    /**
     * Represents the polynomial as a [String] naming variables by [namer] and with brackets around the string if needed
     * (i.e. when there are at least two addends in the representation).
     * Consider that monomials are sorted in **reversed** lexicographic order.
     */
    fun toReversedStringWithBrackets(namer: (Variable) -> String): String =
        with(toReversedString(namer)) { if (coefficients.count() == 1) this else "($this)" }

    /**
     * Converts the value to [LabeledRationalFunction].
     */
    fun toLabeledRationalFunction() = LabeledRationalFunction(this)

    companion object {
        /**
         * Represents internal [LabeledPolynomial] errors.
         */
        private class LabeledPolynomialError: Error {
            constructor() : super()
            constructor(message: String) : super(message)
        }

        /**
         * Returns the same degrees description of the monomial, but without zero degrees.
         */
        internal fun Map<Variable, Int>.cleanUp() = filterValues { it > 0 }

        /**
         * Comparator for lexicographic comparison of monomials.
         */
        val monomialComparator =
            object : Comparator<Map<Variable, Int>> {
                override fun compare(o1: Map<Variable, Int>?, o2: Map<Variable, Int>?): Int {
                    if (o1 == o2) return 0
                    if (o1 == null) return 1
                    if (o2 == null) return -1

                    val commonVariables = (o1.keys union o2.keys).sorted()

                    for (variable in commonVariables) {
                        if (o1.getOrDefault(variable, 0) > o2.getOrDefault(variable, 0)) return -1
                        if (o1.getOrDefault(variable, 0) < o2.getOrDefault(variable, 0)) return 1
                    }

                    return 0
                }
            }

        /**
         * Represents result of division with remainder.
         */
        data class DividingResult<T : Field<T>>(
            val quotient: LabeledPolynomial<T>,
            val reminder: LabeledPolynomial<T>
        )
    }
}