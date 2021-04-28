package math.polynomials

import math.ringsAndFields.*


class LabeledRationalFunction<T : Ring<T>>
    (
    val numerator: LabeledPolynomial<T>,
    val denominator: LabeledPolynomial<T>
) : Field<LabeledRationalFunction<T>> {
    init { if (denominator.isZero()) throw ArithmeticException("/ by zero") }

    val degree by lazy { numerator.degree - denominator.degree }
    val variables by lazy { numerator.variables union denominator.variables }
    val degrees by lazy {
        variables.map {
            it to numerator.degrees.getOrDefault(it, 0) - denominator.degrees.getOrDefault(it, 0)
        }.toMap()
    }
    val countOfVariables by lazy { variables.size }

    internal val ringExemplar get() = denominator.ringExemplar
    internal val ringOne get() = ringExemplar.getOne()
    internal val ringZero get() = ringExemplar.getZero()

    constructor(numerator: Map<Map<Variable, Int>, T>, denominator: Map<Map<Variable, Int>, T>) : this(
        LabeledPolynomial(numerator),
        LabeledPolynomial(denominator)
    )

    constructor(numerator: List<Pair<Map<Variable, Int>, T>>, denominator: List<Pair<Map<Variable, Int>, T>>) : this(
        LabeledPolynomial(numerator.toMap()),
        LabeledPolynomial(denominator.toMap())
    )

    constructor(numerator: LabeledPolynomial<T>) : this(numerator, numerator.getOne())
    constructor(numerator: Map<Map<Variable, Int>, T>) : this(
        LabeledPolynomial(numerator)
    )

    constructor(numerator: List<Pair<Map<Variable, Int>, T>>) : this(
        LabeledPolynomial(numerator.toMap())
    )
    // TODO: Think about other constructors

    override fun getZero(): LabeledRationalFunction<T> = LabeledRationalFunction(numerator.getZero())
    override fun getOne(): LabeledRationalFunction<T> = numerator.getOne().let { LabeledRationalFunction(it, it) }
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

    override operator fun unaryPlus(): LabeledRationalFunction<T> = this

    override operator fun unaryMinus(): LabeledRationalFunction<T> =
        LabeledRationalFunction(-this.numerator, this.denominator)

    override operator fun plus(other: LabeledRationalFunction<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other.denominator + denominator * other.numerator,
            denominator * other.denominator
        )

    operator fun plus(other: LabeledPolynomial<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator + denominator * other,
            denominator
        )

    operator fun plus(other: T): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator + denominator * other,
            denominator
        )

    override operator fun minus(other: LabeledRationalFunction<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other.denominator - denominator * other.numerator,
            denominator * other.denominator
        )

    operator fun minus(other: LabeledPolynomial<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator - denominator * other,
            denominator
        )

    operator fun minus(other: T): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator - denominator * other,
            denominator
        )

    override operator fun times(other: LabeledRationalFunction<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other.numerator,
            denominator * other.denominator
        )

    operator fun times(other: LabeledPolynomial<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other,
            denominator
        )

    operator fun times(other: T): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Integer): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Int): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Long): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun div(other: LabeledRationalFunction<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator * other.denominator,
            denominator * other.numerator
        )

    operator fun div(other: LabeledPolynomial<T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator,
            denominator * other
        )

    operator fun div(other: T): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator,
            denominator * other
        )

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> {
                other as LabeledRationalFunction<*>

                val ringType = numerator.ringExemplar::class.java

                if (other.numerator.coefficients.values.all { ringType.isInstance(it) } &&
                    other.denominator.coefficients.values.all { ringType.isInstance(it) }) {
                    @Suppress("UNCHECKED_CAST")
                    other as LabeledRationalFunction<T>

                    numerator * other.denominator == other.numerator * denominator
                } else false
            }
        }

    override fun hashCode(): Int = javaClass.hashCode()

    operator fun invoke(arg: Map<Variable, T>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator(arg),
            denominator(arg)
        )

    @JvmName("invokeLabeledPolynomial")
    operator fun invoke(arg: Map<Variable, LabeledPolynomial<T>>): LabeledRationalFunction<T> =
        LabeledRationalFunction(
            numerator(arg),
            denominator(arg)
        )

    @JvmName("invokeLabeledRationalFunction")
    operator fun invoke(arg: Map<Variable, LabeledRationalFunction<T>>): LabeledRationalFunction<T> {
        var num = numerator invokeRFTakeNumerator arg
        var den = denominator invokeRFTakeNumerator arg
        for (variable in variables) if (variable in arg) {
            val degreeDif = degrees[variable]!!
            if (degreeDif > 0)
                den = multiplyByPower(den, arg[variable]!!.denominator, degreeDif)
            else
                num = multiplyByPower(num, arg[variable]!!.denominator, -degreeDif)
        }
        return LabeledRationalFunction(num, den)
    }

    override fun toString(): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toString()
            else ->
                (if (numerator.coefficients.count() <= 1) numerator.toString() else "($numerator)") +
                        " / " +
                        (if (denominator.coefficients.count() <= 1) denominator.toString() else "($denominator)")
        }
}