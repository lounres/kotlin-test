package math.polynomials

import math.ringsAndFields.*


class LabeledRationalFunction<T : Ring<T>>(
    val numerator: LabeledPolynomial<T>,
    val denominator: LabeledPolynomial<T>
) : Field<LabeledRationalFunction<T>> {
    init { if (denominator.isZero()) throw ArithmeticException("/ by zero") }

    operator fun component1(): LabeledPolynomial<T> = numerator
    operator fun component2(): LabeledPolynomial<T> = denominator

    val degree by lazy { numerator.degree - denominator.degree }
    val variables by lazy { numerator.variables union denominator.variables }
    val degrees by lazy {
        variables.associateWith {
            numerator.degrees.getOrDefault(it, 0) - denominator.degrees.getOrDefault(it, 0)
        }
    }
    val countOfVariables by lazy { variables.size }

    internal val ringExemplar get() = denominator.ringExemplar
    internal val ringOne get() = ringExemplar.getOne()
    internal val ringZero get() = ringExemplar.getZero()

    constructor(numeratorCoefficients: Map<Map<Variable, Int>, T>, denominatorCoefficients: Map<Map<Variable, Int>, T>) : this(
        LabeledPolynomial(numeratorCoefficients),
        LabeledPolynomial(denominatorCoefficients)
    )

    constructor(numeratorCoefficients: Collection<Pair<Map<Variable, Int>, T>>, denominatorCoefficients: Collection<Pair<Map<Variable, Int>, T>>) : this(
        LabeledPolynomial(numeratorCoefficients),
        LabeledPolynomial(denominatorCoefficients)
    )

    constructor(numerator: LabeledPolynomial<T>) : this(numerator, numerator.getOne())
    constructor(numeratorCoefficients: Map<Map<Variable, Int>, T>) : this(
        LabeledPolynomial(numeratorCoefficients)
    )

    constructor(numeratorCoefficients: Collection<Pair<Map<Variable, Int>, T>>) : this(
        LabeledPolynomial(numeratorCoefficients)
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

    override fun toString(): String = toString(emptyMap())

    fun toString(names: Map<Variable, String> = emptyMap()): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toString(names)
            else -> "${numerator.toStringWithBrackets(names)}/${denominator.toStringWithBrackets(names)}"
        }

    fun toString(namer: (Variable) -> String): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toString(namer)
            else -> "${numerator.toStringWithBrackets(namer)}/${denominator.toStringWithBrackets(namer)}"
        }

    fun toStringWithBrackets(names: Map<Variable, String> = emptyMap()): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toStringWithBrackets(names)
            else -> "(${numerator.toStringWithBrackets(names)}/${denominator.toStringWithBrackets(names)})"
        }

    fun toStringWithBrackets(namer: (Variable) -> String): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toStringWithBrackets(namer)
            else -> "(${numerator.toStringWithBrackets(namer)}/${denominator.toStringWithBrackets(namer)})"
        }

    fun toReversedString(names: Map<Variable, String> = emptyMap()): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toReversedString(names)
            else -> "${numerator.toReversedStringWithBrackets(names)}/${denominator.toReversedStringWithBrackets(names)}"
        }

    fun toReversedString(namer: (Variable) -> String): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toReversedString(namer)
            else -> "${numerator.toReversedStringWithBrackets(namer)}/${denominator.toReversedStringWithBrackets(namer)}"
        }

    fun toReversedStringWithBrackets(names: Map<Variable, String> = emptyMap()): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toReversedStringWithBrackets(names)
            else -> "(${numerator.toReversedStringWithBrackets(names)}/${denominator.toReversedStringWithBrackets(names)})"
        }

    fun toReversedStringWithBrackets(namer: (Variable) -> String): String =
        when (true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toReversedStringWithBrackets(namer)
            else -> "(${numerator.toReversedStringWithBrackets(namer)}/${denominator.toReversedStringWithBrackets(namer)})"
        }
}