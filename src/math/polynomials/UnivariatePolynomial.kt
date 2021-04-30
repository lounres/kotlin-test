package math.polynomials

import math.ringsAndFields.*
import kotlin.math.*


/**
 * The class represents univariate polynomials.
 *
 * @property coefficients List containing coefficients of the polynomial.
 *     In index `i` coefficient for `x^i` is stored.
 *     **There may be zeros in the end of the list. But must be at least one coefficient.**
 * @property degree Represents degree of the polynomial.
 * @property ringExemplar Exemplar of the used field [T]. Used to get one and zero in the field or the field class.
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

    override fun getZero(): UnivariatePolynomial<T> = UnivariatePolynomial(ringZero)
    override fun getOne(): UnivariatePolynomial<T> = UnivariatePolynomial(ringOne)
    override fun isZero(): Boolean = coefficients.all { it.isZero() }
    override fun isOne(): Boolean = coefficients.indexOfLast { it.isNotZero() } == 0

    override operator fun unaryPlus(): UnivariatePolynomial<T> = this

    override operator fun unaryMinus(): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .map { -it }
        )

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
                .let { it.ifEmpty { listOf(ringZero) } }
        )

    operator fun plus(other: T): UnivariatePolynomial<T> =
        if (degree == -1) UnivariatePolynomial(other) else UnivariatePolynomial(
            listOf(coefficients[0] + other) + coefficients.subList(1, degree + 1)
        )

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
                .let { it.ifEmpty { listOf(ringZero) } }
        )

    operator fun minus(other: T): UnivariatePolynomial<T> =
        if (degree == -1) UnivariatePolynomial(-other) else UnivariatePolynomial(
            listOf(coefficients[0] - other) + coefficients.subList(1, degree + 1)
        )

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


    operator fun times(other: T): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )

    override operator fun times(other: Integer): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )

    override operator fun times(other: Int): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )

    override operator fun times(other: Long): UnivariatePolynomial<T> =
        UnivariatePolynomial(
            coefficients
                .subList(0, degree + 1)
                .map { it * other }
        )

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

    override fun hashCode(): Int = javaClass.hashCode()

    operator fun invoke(arg: T): T =
        coefficients
            .asSequence()
            .withIndex()
            .filter { it.value.isNotZero() }
            .map { (index, t) -> multiplyByPower(t, arg, index) }
            .reduce { acc, res -> acc + res }

    operator fun invoke(arg: UnivariatePolynomial<T>): UnivariatePolynomial<T> =
        if (isZero()) this
        else
            coefficients
                .asSequence()
                .withIndex()
                .filter { it.value.isNotZero() }
                .map { (index, t) -> multiplyByPower(UnivariatePolynomial(t), arg, index) }
                .reduce { acc, res -> acc + res }

    operator fun invoke(arg: UnivariateRationalFunction<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            this invokeRFTakeNumerator arg,
            power(arg.denominator, degree)
        )

    override fun toString(): String = toString(variableName)

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
            .run { ifEmpty { "0" } }

    fun toStringWithBrackets(withVariableName: String = variableName): String =
        with(toString(withVariableName)) { if (coefficients.count { it.isNotZero() } <= 1) this else "($this)" }

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
            .run { ifEmpty { "0" } }

    fun toReversedStringWithBrackets(withVariableName: String = variableName): String =
        with(toReversedString(withVariableName)) { if (coefficients.count { it.isNotZero() } == 1) this else "($this)" }

    fun removeZeros() =
        if (degree > -1) UnivariatePolynomial(coefficients.subList(0, degree + 1)) else getZero()

    fun toUnivariateRationalFunction() = UnivariateRationalFunction(this)

    fun toPolynomial() = Polynomial(
        coefficients
            .asSequence()
            .withIndex()
            .filter { (_, t) -> t.isNotZero() }
            .map { (deg, t) -> (if (deg > 0) listOf(deg) else listOf()) to t }
            .toMap(),
        toCheckInput = false
    )

    fun toRationalFunction() = RationalFunction(toPolynomial())

    fun toLabeledPolynomial() = LabeledPolynomial(
        coefficients
            .asSequence()
            .withIndex()
            .filter { (_, t) -> t.isNotZero() }
            .map { (deg, t) -> (if (deg > 0) mapOf(Variable(variableName) to deg) else mapOf()) to t }
            .toMap(),
        toCheckInput = false
    )

    fun toLabeledRationalFunction() = LabeledRationalFunction(toLabeledPolynomial())

    companion object {
        var variableName = "x"

        private class UnivariatePolynomialError(message: String): Error(message)

        data class DividingResult<T : Field<T>>(
            val quotient: UnivariatePolynomial<T>,
            val reminder: UnivariatePolynomial<T>
        )
    }
}
