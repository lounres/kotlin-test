package math.polynomials

import math.ringsAndFields.*
import kotlin.math.max


class Polynomial<T: Ring<T>> internal constructor(coefs: Map<List<Int>, T>, toCheckInput: Boolean) : Ring<Polynomial<T>> {
    val coefficients: Map<List<Int>, T>
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

    val countOfVariables by lazy { if (isZero) 0 else coefficients.keys.maxOf { it.size } }
    val degree by lazy { if (isZero) -1 else coefficients.keys.maxOf { it.sum() } }
    val degrees by lazy { if (isZero) emptyList() else coefficients.keys.fold(List(countOfVariables) { 0 }) { acc, list ->
        acc.mapIndexed { index, i -> max(list.getOrElse(index) { 0 }, i) }
    } }

    internal val ringExemplar get() = coefficients.entries.first().value
    internal val ringOne get() = ringExemplar.getOne()
    internal val ringZero get() = ringExemplar.getZero()

    internal constructor(vararg pairs: Pair<List<Int>, T>, toCheckInput: Boolean) : this(mapOf(*pairs), toCheckInput)
    constructor(coefs: Map<List<Int>, T>) : this(coefs, true)
    constructor(vararg pairs: Pair<List<Int>, T>) : this(mapOf(*pairs), true)

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
                .run { ifEmpty { mapOf(emptyList<Int>() to ringZero) } },
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

    override operator fun minus(other: Polynomial<T>): Polynomial<T> =
        Polynomial(
            coefficients
                .toMutableMap()
                .apply {
                    other.coefficients
                        .mapValuesTo(this) { (key, value) -> if (key in this) this[key]!! - value else -value }
                }
                .filterValues { it.isNotZero() }
                .run { ifEmpty { mapOf(emptyList<Int>() to ringZero) } },
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
                        .filterValues { it.isNotZero() },
                    toCheckInput = false
                )
        }

    operator fun times(other: T): Polynomial<T> =
        if (other.isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other },
                toCheckInput = false
            )

    override operator fun times(other: Integer): Polynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other },
                toCheckInput = false
            )

    override operator fun times(other: Int): Polynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other },
                toCheckInput = false
            )

    override operator fun times(other: Long): Polynomial<T> =
        if ((ringOne * other).isZero()) getZero()
        else
            Polynomial(
                coefficients
                    .mapValues { it.value * other },
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

    override fun hashCode(): Int = javaClass.hashCode()

    operator fun invoke(arg: Map<Int, T>): Polynomial<T> =
        Polynomial(
            coefficients
                .asSequence()
                .map { (degs, c) ->
                    degs.mapIndexed { index, deg -> if (index in arg) 0 else deg } to
                            degs.foldIndexed(c) { index, acc, deg -> if (index in arg) multiplyByPower(acc, arg[index]!!, deg) else acc }
                }
                .toMap()
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
            .reduce { acc, polynomial -> acc + polynomial }

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
        coefficients
            .toSortedMap(variableComparator)
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
            .run { ifEmpty { "0" } }

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

        val variableComparator =
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