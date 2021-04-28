package math.polynomials

import math.ringsAndFields.*
import kotlin.math.max


class RationalFunction<T: Ring<T>> (val numerator: Polynomial<T>, val denominator: Polynomial<T>) : Field<RationalFunction<T>> {
    init { if (denominator.isZero()) throw ArithmeticException("/ by zero") }

    val countOfVariables by lazy { max(numerator.countOfVariables, denominator.countOfVariables) }
    val degree by lazy { numerator.degree - denominator.degree }
    val degrees by lazy { (0 until countOfVariables).map { it to numerator.degrees.getOrElse(it) { 0 } - denominator.degrees.getOrElse(it) { 0 } } }

    internal val ringExemplar get() = denominator.ringExemplar
    internal val ringOne get() = ringExemplar.getOne()
    internal val ringZero get() = ringExemplar.getZero()

    constructor(numerator: Map<List<Int>, T>, denominator: Map<List<Int>, T>) : this(
        Polynomial(numerator),
        Polynomial(denominator)
    )
    constructor(numerator: List<Pair<List<Int>, T>>, denominator: List<Pair<List<Int>, T>>) : this(
        Polynomial(numerator.toMap()),
        Polynomial(denominator.toMap())
    )
    constructor(numerator: Polynomial<T>) : this(numerator, numerator.getOne())
    constructor(numerator: Map<List<Int>, T>) : this(
        Polynomial(numerator)
    )
    constructor(numerator: List<Pair<List<Int>, T>>) : this(
        Polynomial(numerator.toMap())
    )
    // TODO: Think about other constructors

    override fun getZero(): RationalFunction<T> = RationalFunction(numerator.getZero())
    override fun getOne(): RationalFunction<T> = numerator.getOne().let { RationalFunction(it, it) }
    override fun isZero(): Boolean = numerator.isZero()
    override fun isOne(): Boolean = numerator.coefficients.keys == denominator.coefficients.keys &&
            with(numerator.coefficients) {
                val degs = keys.toMutableSet()
                val someDeg = keys.first()
                degs.remove(someDeg)
                val thisCoef = this[someDeg]!!
                val otherCoef = denominator.coefficients[someDeg]!!
                degs.all { this[it]!! * otherCoef == thisCoef * denominator.coefficients[it]!! }
            }

    override operator fun unaryPlus(): RationalFunction<T> = this

    override operator fun unaryMinus(): RationalFunction<T> = RationalFunction(-this.numerator, this.denominator)

    override operator fun plus(other: RationalFunction<T>): RationalFunction<T> =
        RationalFunction(
            numerator * other.denominator + denominator * other.numerator,
            denominator * other.denominator
        )

    operator fun plus(other: Polynomial<T>): RationalFunction<T> =
        RationalFunction(
            numerator + denominator * other,
            denominator
        )

    operator fun plus(other: T): RationalFunction<T> =
        RationalFunction(
            numerator + denominator * other,
            denominator
        )

    override operator fun minus(other: RationalFunction<T>): RationalFunction<T> =
        RationalFunction(
            numerator * other.denominator - denominator * other.numerator,
            denominator * other.denominator
        )

    operator fun minus(other: Polynomial<T>): RationalFunction<T> =
        RationalFunction(
            numerator - denominator * other,
            denominator
        )

    operator fun minus(other: T): RationalFunction<T> =
        RationalFunction(
            numerator - denominator * other,
            denominator
        )

    override operator fun times(other: RationalFunction<T>): RationalFunction<T> =
        RationalFunction(
            numerator * other.numerator,
            denominator * other.denominator
        )

    operator fun times(other: Polynomial<T>): RationalFunction<T> =
        RationalFunction(
            numerator * other,
            denominator
        )

    operator fun times(other: T): RationalFunction<T> =
        RationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Integer): RationalFunction<T> =
        RationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Int): RationalFunction<T> =
        RationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Long): RationalFunction<T> =
        RationalFunction(
            numerator * other,
            denominator
        )

    override operator fun div(other: RationalFunction<T>): RationalFunction<T> =
        RationalFunction(
            numerator * other.denominator,
            denominator * other.numerator
        )

    operator fun div(other: Polynomial<T>): RationalFunction<T> =
        RationalFunction(
            numerator,
            denominator * other
        )

    operator fun div(other: T): RationalFunction<T> =
        RationalFunction(
            numerator,
            denominator * other
        )

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> {
                other as RationalFunction<*>

                val ringType = numerator.ringExemplar::class.java

                if (other.numerator.coefficients.values.all { ringType.isInstance(it) } &&
                    other.denominator.coefficients.values.all { ringType.isInstance(it) }) {
                    @Suppress("UNCHECKED_CAST")
                    other as RationalFunction<T>

                    numerator * other.denominator == other.numerator * denominator
                } else false
            }
        }

    override fun hashCode(): Int = javaClass.hashCode()

    operator fun invoke(arg: Map<Int, T>): RationalFunction<T> =
        RationalFunction(
            numerator(arg),
            denominator(arg)
        )

    @JvmName("invokePolynomial")
    operator fun invoke(arg: Map<Int, Polynomial<T>>): RationalFunction<T> =
        RationalFunction(
            numerator(arg),
            denominator(arg)
        )

    @JvmName("invokeRationalFunction")
    operator fun invoke(arg: Map<Int, RationalFunction<T>>): RationalFunction<T> {
        var num = numerator invokeRFTakeNumerator arg
        var den = denominator invokeRFTakeNumerator arg
        for (variable in 0 until max(numerator.countOfVariables, denominator.countOfVariables)) if (variable in arg) {
            val degreeDif = numerator.degrees.getOrElse(variable) { 0 } - denominator.degrees.getOrElse(variable) { 0 }
            if (degreeDif > 0)
                den = multiplyByPower(den, arg[variable]!!.denominator, degreeDif)
            else
                num = multiplyByPower(num, arg[variable]!!.denominator, -degreeDif)
        }
        return RationalFunction(num, den)
    }

    override fun toString(): String =
        when(true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toString()
            else ->
                (if (numerator.coefficients.count() <= 1) numerator.toString() else "($numerator)") +
                        " / " +
                        (if (denominator.coefficients.count() <= 1) denominator.toString() else "($denominator)")
        }

    fun toLabeledRationalFunction() = LabeledRationalFunction(numerator.toLabeledPolynomial(), denominator.toLabeledPolynomial())
}