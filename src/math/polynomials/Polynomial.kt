package math.polynomials

import math.polynomials.LabeledPolynomial.Companion.cleanUp
import math.polynomials.Polynomial.Companion.cleanUp
import math.ringsAndFields.*
import kotlin.math.max


class Polynomial<T: Ring<T>> internal constructor(coefs: Map<List<Int>, T>, toCheckInput: Boolean) : Ring<Polynomial<T>> {
    val coefficients: Map<List<Int>, T>
    /**
     * Signaling if the instance is zero polynomial and as consequence if it is special case.
     *
     * To check for equality to the zero polynomial use [isZero].
     */
    private val isZero: Boolean

    init {
        if (toCheckInput) {
            if (coefs.isEmpty()) throw PolynomialError("Polynomial must contain at least one (maybe zero) monomial")
            if (coefs.keys.any { it.any { it < 0 } }) throw PolynomialError("All monomials should contain only non-negative degrees")

            val ringExemplar = coefs.entries.first().value
            val fixedCoefs = mutableMapOf<List<Int>, T>()

            for (entry in coefs) {
                val key = entry.key.cleanUp()
                val value = entry.value
                fixedCoefs[key] = if (key in fixedCoefs) fixedCoefs[key]!! + value else value
            }

            coefficients = fixedCoefs
                .filter { it.value.isNotZero() }
                .let {
                    if (it.isNotEmpty()) {
                        isZero = false
                        it
                    } else {
                        isZero = true
                        mapOf(emptyList<Int>() to ringExemplar.getZero())
                    }
                }
        } else {
            coefficients = coefs
            isZero = (coefs.all { it.value.isZero() })
        }
    }

    /**
     * Count of all variables that appear in the polynomial in positive exponents.
     */
    val countOfVariables: Int by lazy { if (isZero) 0 else coefficients.keys.maxOf { it.size } }
    /**
     * Degree of the polynomial, [see also](https://en.wikipedia.org/wiki/Degree_of_a_polynomial). If the polynomial is
     * zero, degree is -1.
     */
    val degree: Int by lazy { if (isZero) -1 else coefficients.keys.maxOf { it.sum() } }
    /**
     * List that associates indices of variables (that appear in the polynomial in positive exponents) with their most
     * exponents in which the variables are appeared in the polynomial.
     *
     * As consequence all values in the list are non-negative integers. Also if the polynomial is constant, the list is empty.
     * And size of the list is [countOfVariables].
     */
    val degrees: List<Int> by lazy { if (isZero) emptyList() else coefficients.keys.fold(List(countOfVariables) { 0 }) { acc, list ->
        acc.mapIndexed { index, i -> max(list.getOrElse(index) { 0 }, i) }
    } }

    /**
     * Simple way to access any exemplar of the ring. Used to get [ringZero] and [ringOne] &ndash; zero and one of the ring.
     */
    internal val ringExemplar: T get() = coefficients.entries.first().value
    /**
     * Simple way to access the zero of the ring.
     */
    internal val ringOne: T get() = ringExemplar.getOne()
    /**
     * Simple way to access the one of the ring.
     */
    internal val ringZero: T get() = ringExemplar.getZero()

    internal constructor(pairs: Collection<Pair<List<Int>, T>>, toCheckInput: Boolean) : this(
        if (toCheckInput) {
            if (pairs.isEmpty()) throw PolynomialError("Polynomial must contain at least one (maybe zero) monomial")
            if (pairs.any { it.first.any { it < 0 } }) throw PolynomialError("All monomials should contain only non-negative degrees")

            val ringExemplar = pairs.first().second
            val fixedCoefs = mutableMapOf<List<Int>, T>()

            for (entry in pairs) {
                val key = entry.first.cleanUp()
                val value = entry.second
                fixedCoefs[key] = if (key in fixedCoefs) fixedCoefs[key]!! + value else value
            }

            fixedCoefs
                .filter { it.value.isNotZero() }
                .ifEmpty {
                    mapOf(emptyList<Int>() to ringExemplar.getZero())
                }
        } else pairs.toMap(),
        toCheckInput = false
    )
    internal constructor(vararg pairs: Pair<List<Int>, T>, toCheckInput: Boolean) : this(pairs.toList(), toCheckInput)
    constructor(coefs: Map<List<Int>, T>) : this(coefs, toCheckInput = true)
    constructor(pairs: Collection<Pair<List<Int>, T>>) : this(pairs, toCheckInput = true)
    constructor(vararg pairs: Pair<List<Int>, T>) : this(*pairs, toCheckInput = true)

    override fun getZero(): Polynomial<T> = Polynomial(emptyList<Int>() to ringZero, toCheckInput = false)
    override fun getOne(): Polynomial<T> = Polynomial(emptyList<Int>() to ringOne, toCheckInput = false)
    override fun isZero(): Boolean = isZero
    override fun isOne(): Boolean = !isZero && coefficients.keys.all { it.isEmpty() }

    override operator fun unaryPlus(): Polynomial<T> = this

    override operator fun unaryMinus(): Polynomial<T> =
        Polynomial(
            coefficients
                .mapValues { -it.value },
            toCheckInput = false
        )

    override operator fun plus(other: Polynomial<T>): Polynomial<T> =
        Polynomial(
            coefficients
                .toMutableMap()
                .apply {
                    other.coefficients
                        .mapValuesTo(this) { (key, value) -> if (key in this) this[key]!! + value else value }
                }
                .filterValues { it.isNotZero() }
                .ifEmpty { mapOf(emptyList<Int>() to ringZero) },
            toCheckInput = false
        )

    operator fun plus(other: T): Polynomial<T> =
        if (other.isZero()) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun plus(other: Integer): Polynomial<T> =
        if (other.isZero()) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun plus(other: Int): Polynomial<T> =
        if (other == 0) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun plus(other: Long): Polynomial<T> =
        if (other == 0L) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun minus(other: Polynomial<T>): Polynomial<T> =
        Polynomial(
            coefficients
                .toMutableMap()
                .apply {
                    other.coefficients
                        .mapValuesTo(this) { (key, value) -> if (key in this) this[key]!! - value else -value }
                }
                .filterValues { it.isNotZero() }
                .ifEmpty { mapOf(emptyList<Int>() to ringZero) },
            toCheckInput = false
        )

    operator fun minus(other: T): Polynomial<T> =
        if (other.isZero()) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun minus(other: Integer): Polynomial<T> =
        if (other.isZero()) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun minus(other: Int): Polynomial<T> =
        if (other == 0) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun minus(other: Long): Polynomial<T> =
        if (other == 0L) this
        else
            Polynomial(
                coefficients
                    .toMutableMap()
                    .apply {
                        val key = emptyList<Int>()
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

    override operator fun times(other: Polynomial<T>): Polynomial<T> =
        when {
            isZero() -> this
            other.isZero() -> other
            else ->
                Polynomial(
                    mutableMapOf<List<Int>, T>()
                        .apply {
                            for ((degs1, c1) in coefficients) for ((degs2, c2) in other.coefficients) {
                                val degs =
                                    (0..max(degs1.lastIndex, degs2.lastIndex))
                                        .map { degs1.getOrElse(it) { 0 } + degs2.getOrElse(it) { 0 } }
                                val c = c1 * c2
                                this[degs] = if (degs in this) this[degs]!! + c else c
                            }
                        }
                        .filterValues { it.isNotZero() }
                        .ifEmpty { mapOf(emptyList<Int>() to ringZero) },
                    toCheckInput = false
                )
        }

    operator fun times(other: T): Polynomial<T> =
        if (other.isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyList<Int>() to ringZero) },
                toCheckInput = false
            )

    override operator fun times(other: Integer): Polynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyList<Int>() to ringZero) },
                toCheckInput = false
            )

    override operator fun times(other: Int): Polynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyList<Int>() to ringZero) },
                toCheckInput = false
            )

    override operator fun times(other: Long): Polynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other }
                    .filterValues { it.isNotZero() }
                    .ifEmpty { mapOf(emptyList<Int>() to ringZero) },
                toCheckInput = false
            )

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> {
                other as Polynomial<*>

                val ringType = ringExemplar::class.java

                if (other.coefficients.values.all { ringType.isInstance(it) }) {
                    @Suppress("UNCHECKED_CAST")
                    other as Polynomial<T>

                    coefficients.size == other.coefficients.size &&
                            coefficients.all { (key, value) -> with(other.coefficients) { key in this && this[key] == value } }
                } else false
            }
        }

    override fun hashCode(): Int = javaClass.hashCode() // TODO: Rewrite

    operator fun invoke(arg: Map<Int, T>): Polynomial<T> =
        Polynomial(
            coefficients
                .map { (degs, c) ->
                    degs.mapIndexed { index, deg -> if (index in arg) 0 else deg } to
                            degs.foldIndexed(c) { index, acc, deg -> if (index in arg) multiplyByPower(acc, arg[index]!!, deg) else acc }
                }
        )

    @JvmName("invokePolynomial")
    operator fun invoke(arg: Map<Int, Polynomial<T>>): Polynomial<T> =
        coefficients
            .asSequence()
            .map { (degs, c) ->
                degs.foldIndexed(
                    Polynomial(
                        degs.mapIndexed { index, deg -> if (index in arg) 0 else deg } to c
                    )
                ) { index, acc, deg -> if (index in arg) multiplyByPower(acc, arg[index]!!, deg) else acc }
            }
            .reduce { acc, polynomial -> acc + polynomial } // TODO: Rewrite. Might be slow.

    @JvmName("invokeRationalFunction")
    operator fun invoke(arg: Map<Int, RationalFunction<T>>): RationalFunction<T> =
        RationalFunction(
            this invokeRFTakeNumerator arg,
            (0 until countOfVariables)
                .asSequence()
                .filter { it in arg }
                .map { power(arg[it]!!.denominator, degree) }
                .fold(getOne()) { acc, polynomial ->  acc * polynomial }
        )

    override fun toString(): String = toString(variableName)

    fun toString(withVariableName: String = variableName): String =
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
                                .mapIndexed { index, deg ->
                                    when (deg) {
                                        0 -> ""
                                        1 -> "${withVariableName}_${index+1}"
                                        else -> "${withVariableName}_${index+1}^$deg"
                                    }
                                }
                                .filter { it.isNotEmpty() }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    fun toString(namer: (Int) -> String): String =
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
                                .mapIndexed { index, deg ->
                                    when (deg) {
                                        0 -> ""
                                        1 -> namer(index)
                                        else -> "${namer(index)}^$deg"
                                    }
                                }
                                .filter { it.isNotEmpty() }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    fun toStringWithBrackets(withVariableName: String = variableName): String =
        with(toString(withVariableName)) { if (coefficients.count() == 1) this else "($this)" }

    fun toStringWithBrackets(namer: (Int) -> String): String =
        with(toString(namer)) { if (coefficients.count() == 1) this else "($this)" }

    fun toReversedString(withVariableName: String = variableName): String =
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
                                .mapIndexed { index, deg ->
                                    when (deg) {
                                        0 -> ""
                                        1 -> "${withVariableName}_${index+1}"
                                        else -> "${withVariableName}_${index+1}^$deg"
                                    }
                                }
                                .filter { it.isNotEmpty() }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    fun toReversedString(namer: (Int) -> String): String =
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
                                .mapIndexed { index, deg ->
                                    when (deg) {
                                        0 -> ""
                                        1 -> namer(index)
                                        else -> "${namer(index)}^$deg"
                                    }
                                }
                                .filter { it.isNotEmpty() }
                                .joinToString(separator = " ") { it }
                }
            }
            .joinToString(separator = " + ") { it }
            .ifEmpty { "0" }

    fun toReversedStringWithBrackets(withVariableName: String = variableName): String =
        with(toReversedString(withVariableName)) { if (coefficients.count() == 1) this else "($this)" }

    fun toReversedStringWithBrackets(namer: (Int) -> String): String =
        with(toReversedString(namer)) { if (coefficients.count() == 1) this else "($this)" }

    fun toRationalFunction() = RationalFunction(this)

    fun toLabeledPolynomial() = LabeledPolynomial(
        coefficients
            .asSequence()
            .map { (degs, t) ->
                degs
                    .asSequence()
                    .withIndex()
                    .filter { (_, deg) -> deg > 0 }
                    .map { (variable, deg) -> Variable("${variableName}_$variable") to deg }
                    .toMap() to t
            }
            .toMap(),
        toCheckInput = false
    )

    fun toLabeledRationalFunction() = LabeledRationalFunction(toLabeledPolynomial())

    companion object {
        var variableName = "x"

        private class PolynomialError(message: String): Error(message)

        internal fun List<Int>.cleanUp() = subList(0, indexOfLast { it != 0 } + 1)

        val monomialComparator =
            object : Comparator<List<Int>> {
                override fun compare(o1: List<Int>?, o2: List<Int>?): Int {
                    if (o1 == o2) return 0
                    if (o1 == null) return 1
                    if (o2 == null) return -1

                    val countOfVariables = max(o1.size, o2.size)

                    for (variable in 0 until countOfVariables) {
                        if (o1.getOrElse(variable) { 0 } > o2.getOrElse(variable) { 0 }) return -1
                        if (o1.getOrElse(variable) { 0 } < o2.getOrElse(variable) { 0 }) return 1
                    }

                    return 0
                }
            }

        data class DividingResult<T : Field<T>>(
            val quotient: Polynomial<T>,
            val reminder: Polynomial<T>
        )
    }
}