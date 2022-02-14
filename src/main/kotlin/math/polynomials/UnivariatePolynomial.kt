package math.polynomials

import math.ringsAndFields.*
import kotlin.math.*
import kotlin.reflect.KProperty

/**
 * Represents multivariate polynomials with indexed variables.
 *
 * @param T Ring in which the polynomial is considered.
 * @param coefs Coefficients of the instants.
 *
 * @constructor Cleans the received [coefficients]: removes zero degrees from end of received list, and if result is
 * empty map adds only element in it.
 *
 * @throws UnivariatePolynomialError If no coefficient received or if any of degrees in any monomial is negative.
 */
class UnivariatePolynomial<T: Ring<T>> (val coefficients: List<T>) /* TODO: Add toCheckInput parameter. */ : Ring<UnivariatePolynomial<T>> {
    init { if (coefficients.isEmpty()) throw UnivariatePolynomialError("UnivariatePolynomial coefficients' list must not be empty") }

    /**
     * Degree of the polynomial, [see also](https://en.wikipedia.org/wiki/Degree_of_a_polynomial). If the polynomial is
     * zero, degree is -1.
     */
    val degree: Int by lazy { coefficients.indexOfLast { it.isNotZero() } }

    /**
     * Simple way to access any exemplar of the ring. Used to get [ringZero] and [ringOne] &ndash; zero and one of the ring.
     */
    internal val ringExemplar: T get() = coefficients.first()
    /**
     * Simple way to access the zero of the ring.
     */
    internal val ringOne: T get() = ringExemplar.getOne()
    /**
     * Simple way to access the one of the ring.
     */
    internal val ringZero: T get() = ringExemplar.getZero()

    constructor(coefficients: List<T>, reverse: Boolean = false) : this(with(coefficients) { if (reverse) reversed() else this })
    constructor(vararg coefficients: T, reverse: Boolean = false) : this(coefficients.toList(), reverse)

    /**
     * Returns the zero polynomial (additive identity) over considered ring.
     */
    override fun getZero(): UnivariatePolynomial<T> = UnivariatePolynomial(ringZero)
    /**
     * Returns the unit polynomial (multiplicative identity) over considered ring.
     */
    override fun getOne(): UnivariatePolynomial<T> = UnivariatePolynomial(ringOne)
    /**
     * Checks if the instant is the zero polynomial (additive identity) over considered ring.
     */
    override fun isZero(): Boolean = coefficients.all { it.isZero() }
    /**
     * Checks if the instant is the unit polynomial (multiplicative identity) over considered ring.
     */
    override fun isOne(): Boolean = degree == 0 && coefficients.first().isOne()

    /**
     * Checks if the instant is constant polynomial (of degree no more than 0) over considered ring.
     */
    fun isConstant(): Boolean = degree <= 0
    /**
     * Checks if the instant is **not** constant polynomial (of degree no more than 0) over considered ring.
     */
    fun isNotConstant(): Boolean = !isConstant()
    /**
     * Checks if the instant is constant non-zero polynomial (of degree no more than 0) over considered ring.
     */
    fun isNonZeroConstant(): Boolean = degree == 0
    /**
     * Checks if the instant is **not** constant non-zero polynomial (of degree no more than 0) over considered ring.
     */
    fun isNotNonZeroConstant(): Boolean = !isNonZeroConstant()

    /**
     * Returns the same polynomial.
     */
    override operator fun unaryPlus(): UnivariatePolynomial<T> = this

    /**
     * Returns negation of the polynomial.
     */
    override operator fun unaryMinus(): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .map { -it }
        )

    /**
     * Returns sum of the polynomials.
     */
    override operator fun plus(other: UnivariatePolynomial<T>): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            (0..max(degree, other.degree))
                .map {
                    when {
                        it > degree -> other.coefficients[it]
                        it > other.degree -> coefficients[it]
                        else -> coefficients[it] + other.coefficients[it]
                    }
                }
                .ifEmpty { listOf(ringZero) }
        )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    operator fun plus(other: T): UnivariatePolynomial<T> =
        if (degree == -1) UnivariatePolynomial(other) else UnivariatePolynomial(
            listOf(coefficients[0] + other) + coefficients.subList(1, degree + 1)
        )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun plus(other: Integer): UnivariatePolynomial<T> =
        if (other.isZero()) this
        else
            UnivariatePolynomial(
                coefficients
                    .toMutableList()
                    .apply {
                        this[0] += ringOne * other
                    }
            )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun plus(other: Int): UnivariatePolynomial<T> =
        if (other == 0) this
        else
            UnivariatePolynomial(
                coefficients
                    .toMutableList()
                    .apply {
                        this[0] += ringOne * other
                    }
            )

    /**
     * Returns sum of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun plus(other: Long): UnivariatePolynomial<T> =
        if (other == 0L) this
        else
            UnivariatePolynomial(
                coefficients
                    .toMutableList()
                    .apply {
                        this[0] += ringOne * other
                    }
            )

    /**
     * Returns difference of the polynomials.
     */
    override operator fun minus(other: UnivariatePolynomial<T>): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            (0..max(degree, other.degree))
                .map {
                    when {
                        it > degree -> -other.coefficients[it]
                        it > other.degree -> coefficients[it]
                        else -> coefficients[it] - other.coefficients[it]
                    }
                }
                .ifEmpty { listOf(ringZero) }
        )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    operator fun minus(other: T): UnivariatePolynomial<T> =
        if (degree == -1) UnivariatePolynomial(-other) else UnivariatePolynomial(
            listOf(coefficients[0] - other) + coefficients.subList(1, degree + 1)
        )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun minus(other: Integer): UnivariatePolynomial<T> =
        if (other.isZero()) this
        else
            UnivariatePolynomial(
                coefficients
                    .toMutableList()
                    .apply {
                        this[0] -= ringOne * other
                    }
            )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun minus(other: Int): UnivariatePolynomial<T> =
        if (other == 0) this
        else
            UnivariatePolynomial(
                coefficients
                    .toMutableList()
                    .apply {
                        this[0] -= ringOne * other
                    }
            )

    /**
     * Returns difference of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun minus(other: Long): UnivariatePolynomial<T> =
        if (other == 0L) this
        else
            UnivariatePolynomial(
                coefficients
                    .toMutableList()
                    .apply {
                        this[0] -= ringOne * other
                    }
            )

    /**
     * Returns product of the polynomials.
     */
    override operator fun times(other: UnivariatePolynomial<T>): UnivariatePolynomial<T> =
        when {
            degree == -1 -> this
            other.degree == -1 -> other
            else ->
                UnivariatePolynomial(
                    (0..(degree + other.degree))
                        .map { d ->
                            (max(0, d - other.degree)..(min(degree, d)))
                                .map { coefficients[it] * other.coefficients[d - it] }
                                .reduce { acc, rational -> acc + rational }
                        }
                )
        }

    /**
     * Returns product of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    operator fun times(other: T): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )
    /**
     * Returns product of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun times(other: Integer): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )

    /**
     * Returns product of the polynomials. [other] is interpreted as [UnivariatePolynomial].
     */
    override operator fun times(other: Int): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )

    /**
     * Returns product of the polynomials. [other] is interpreted as [Polynomial].
     */
    override operator fun times(other: Long): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )

    // TODO: Docs
    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> {
                other as UnivariatePolynomial<*>

                val ringType = ringExemplar::class.java

                if (other.coefficients.all { ringType.isInstance(it) }) {
                    @Suppress("UNCHECKED_CAST")
                    other as UnivariatePolynomial<T>

                    if (this.degree == other.degree) {
                        (0..degree)
                            .all { coefficients[it] == other.coefficients[it] }
                    } else false
                } else false
            }
        }

    // TODO: Docs
    override fun hashCode(): Int = javaClass.hashCode()

    /**
     * Returns polynomial that is got as result of substitution by [arg] instead of the variable.
     */
    operator fun invoke(arg: T): T =
        coefficients
            .asSequence()
            .withIndex()
            .filter { it.value.isNotZero() }
            .map { (index, t) -> multiplyByPower(t, arg, index) }
            .reduce { acc, res -> acc + res }

    /**
     * Returns polynomial that is got as result of substitution by [arg] instead of the variable.
     */
    operator fun invoke(arg: UnivariatePolynomial<T>): UnivariatePolynomial<T> =
        if (isZero()) this
        else
            coefficients
                .asSequence()
                .withIndex()
                .filter { it.value.isNotZero() }
                .map { (index, t) -> multiplyByPower(UnivariatePolynomial(t), arg, index) }
                .reduce { acc, res -> acc + res }

    /**
     * Returns polynomial that is got as result of substitution by [arg] instead of the variable.
     */
    operator fun invoke(arg: UnivariateRationalFunction<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            this invokeRFTakeNumerator arg,
            power(arg.denominator, degree)
        )

    /**
     * Represents the polynomial as a [String]. Consider that monomials are sorted in lexicographic order.
     */
    override fun toString(): String = toString(variableName)

    /**
     * Represents the polynomial as a [String] where name of the variable is [withVariableName].
     * Consider that monomials are sorted in lexicographic order.
     */
    fun toString(withVariableName: String = variableName): String =
        coefficients
            .asSequence()
            .withIndex()
            .filter { it.value.isNotZero() }
            .map { (index, t) ->
                when (index) {
                    0 -> "$t"
                    1 -> when {
                        t.isOne() -> withVariableName
                        t == -t.getOne() -> "-$withVariableName"
                        else -> "$t $withVariableName"
                    }
                    else -> when {
                        t.isOne() -> "$withVariableName^$index"
                        t == -t.getOne() -> "-$withVariableName^$index"
                        else -> "$t $withVariableName^$index"
                    }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    /**
     * Represents the polynomial as a [String] where name of the variable is [withVariableName]
     * and with brackets around the string if needed (i.e. when there are at least two addends in the representation).
     * Consider that monomials are sorted in lexicographic order.
     */
    fun toStringWithBrackets(withVariableName: String = variableName): String =
        with(toString(withVariableName)) { if (coefficients.count { it.isNotZero() } <= 1) this else "($this)" }

    /**
     * Represents the polynomial as a [String] where name of the variable is [withVariableName].
     * Consider that monomials are sorted in **reversed** lexicographic order.
     */
    fun toReversedString(withVariableName: String = variableName): String =
        coefficients
            .asSequence()
            .withIndex()
            .filter { it.value.isNotZero() }
            .toList()
            .asReversed()
            .asSequence()
            .map { (index, t) ->
                when (index) {
                    0 -> "$t"
                    1 -> when {
                        t.isOne() -> withVariableName
                        t == -t.getOne() -> "-$withVariableName"
                        else -> "$t $withVariableName"
                    }
                    else -> when {
                        t.isOne() -> "$withVariableName^$index"
                        t == -t.getOne() -> "-$withVariableName^$index"
                        else -> "$t $withVariableName^$index"
                    }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    /**
     * Represents the polynomial as a [String] where name of the variable is [withVariableName]
     * and with brackets around the string if needed (i.e. when there are at least two addends in the representation).
     * Consider that monomials are sorted in **reversed** lexicographic order.
     */
    fun toReversedStringWithBrackets(withVariableName: String = variableName): String =
        with(toReversedString(withVariableName)) { if (coefficients.count { it.isNotZero() } == 1) this else "($this)" }

    // TODO: Docs
    fun removeZeros() =
        if (degree > -1) UnivariatePolynomial(coefficients.subList(0, degree + 1)) else getZero()

    /**
     * Converts the value to [UnivariateRationalFunction].
     */
    fun toUnivariateRationalFunction() = UnivariateRationalFunction(this)

    /**
     * Converts the value to [Polynomial].
     */
    fun toPolynomial() = Polynomial(
        coefficients
            .asSequence()
            .withIndex()
            .filter { (_, t) -> t.isNotZero() }
            .map { (deg, t) -> (if (deg > 0) listOf(deg) else listOf()) to t }
            .toMap(),
        toCheckInput = false
    )

    /**
     * Converts the value to [RationalFunction].
     */
    fun toRationalFunction() = RationalFunction(toPolynomial())

    /**
     * Converts the value to [LabeledPolynomial].
     */
    fun toLabeledPolynomial() = LabeledPolynomial(
        coefficients
            .asSequence()
            .withIndex()
            .filter { (_, t) -> t.isNotZero() }
            .map { (deg, t) -> (if (deg > 0) mapOf(Variable(variableName) to deg) else mapOf()) to t }
            .toMap(),
        toCheckInput = false
    )

    /**
     * Converts the value to [LabeledRationalFunction].
     */
    fun toLabeledRationalFunction() = LabeledRationalFunction(toLabeledPolynomial())

    companion object {
        /**
         * Default name of variables used in string representations.
         *
         * @see UnivariatePolynomial.toString
         */
        var variableName = "x"

        /**
         * Represents internal [UnivariatePolynomial] errors.
         */
        private class UnivariatePolynomialError(message: String): Error(message)

        /**
         * Represents result of division with remainder.
         */
        data class DividingResult<T : Field<T>>(
            val quotient: UnivariatePolynomial<T>,
            val reminder: UnivariatePolynomial<T>
        )
    }
}
