package math.polynomials

import math.ringsAndFields.*


class UnivariateRationalFunction<T: Ring<T>> (val numerator: UnivariatePolynomial<T>, val denominator: UnivariatePolynomial<T>) : Field<UnivariateRationalFunction<T>> {
    init { if (denominator.isZero()) throw ArithmeticException("/ by zero") }

    val numeratorDegree get() = numerator.degree
    val denominatorDegree get() = denominator.degree
    val degree by lazy { numeratorDegree - denominatorDegree }

    internal val ringExemplar get() = denominator.ringExemplar
    internal val ringOne get() = ringExemplar.getOne()
    internal val ringZero get() = ringExemplar.getZero()

    constructor(numeratorCoefficients: List<T>, denominatorCoefficients: List<T>, reverse: Boolean = false) : this(
        UnivariatePolynomial( with(numeratorCoefficients) { if (reverse) reversed() else this } ),
        UnivariatePolynomial( with(denominatorCoefficients) { if (reverse) reversed() else this } )
    )
    constructor(numerator: UnivariatePolynomial<T>) : this(numerator, numerator.getOne())
    constructor(numeratorCoefficients: List<T>, reverse: Boolean = false) : this(
        UnivariatePolynomial( with(numeratorCoefficients) { if (reverse) reversed() else this } )
    )
    // TODO: Think about other constructors

    override fun getZero(): UnivariateRationalFunction<T> = UnivariateRationalFunction(numerator.getZero())
    override fun getOne(): UnivariateRationalFunction<T> = numerator.getOne().let { UnivariateRationalFunction(it, it) }
    override fun isZero(): Boolean = numerator.isZero()
    override fun isOne(): Boolean = numeratorDegree == denominatorDegree &&
            with(numerator.coefficients) {
                val thisCoef = this[numeratorDegree]
                val otherCoef = denominator.coefficients[numeratorDegree]
                (0..numeratorDegree).all { this[it] * otherCoef == thisCoef * denominator.coefficients[it] }
            }

    override operator fun unaryPlus(): UnivariateRationalFunction<T> = this

    override operator fun unaryMinus(): UnivariateRationalFunction<T> = UnivariateRationalFunction(-this.numerator, this.denominator)

    override operator fun plus(other: UnivariateRationalFunction<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other.denominator + denominator * other.numerator,
            denominator * other.denominator
        )

    operator fun plus(other: UnivariatePolynomial<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator + denominator * other,
            denominator
        )

    operator fun plus(other: T): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator + denominator * other,
            denominator
        )

    override operator fun minus(other: UnivariateRationalFunction<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other.denominator - denominator * other.numerator,
            denominator * other.denominator
        )

    operator fun minus(other: UnivariatePolynomial<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator - denominator * other,
            denominator
        )

    operator fun minus(other: T): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator - denominator * other,
            denominator
        )

    override operator fun times(other: UnivariateRationalFunction<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other.numerator,
            denominator * other.denominator
        )

    operator fun times(other: UnivariatePolynomial<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other,
            denominator
        )

    operator fun times(other: T): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Integer): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Int): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun times(other: Long): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other,
            denominator
        )

    override operator fun div(other: UnivariateRationalFunction<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator * other.denominator,
            denominator * other.numerator
        )

    operator fun div(other: UnivariatePolynomial<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator,
            denominator * other
        )

    operator fun div(other: T): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator,
            denominator * other
        )

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> {
                other as UnivariateRationalFunction<*>

                val ringType = numerator.ringExemplar::class.java

                if (other.numerator.coefficients.all { ringType.isInstance(it) } &&
                    other.denominator.coefficients.all { ringType.isInstance(it) }) {
                    @Suppress("UNCHECKED_CAST")
                    other as UnivariateRationalFunction<T>

                    numerator * other.denominator == other.numerator * denominator
                } else false
            }
        }

    override fun hashCode(): Int = javaClass.hashCode()

    operator fun invoke(arg: UnivariatePolynomial<T>): UnivariateRationalFunction<T> =
        UnivariateRationalFunction(
            numerator(arg),
            denominator(arg)
        )

    operator fun invoke(arg: UnivariateRationalFunction<T>): UnivariateRationalFunction<T> {
        val num = numerator invokeRFTakeNumerator arg
        val den = denominator invokeRFTakeNumerator arg
        val degreeDif = numeratorDegree - denominatorDegree
        return if (degreeDif > 0)
            UnivariateRationalFunction(
                num,
                multiplyByPower(den, arg.denominator, degreeDif)
            )
        else
            UnivariateRationalFunction(
                multiplyByPower(num, arg.denominator, -degreeDif),
                den
            )
    }

    override fun toString(): String =
        when(true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toString()
            else ->
                (if (numerator.coefficients.count { it.isNotZero() } <= 1) numerator.toString() else "($numerator)") +
                        " / " +
                        (if (denominator.coefficients.count { it.isNotZero() } <= 1) denominator.toString() else "($denominator)")
        }


    fun toReversedString(): String =
        when(true) {
            numerator.isZero() -> "0"
            denominator.isOne() -> numerator.toReversedString()
            else ->
                (if (numerator.coefficients.count { it.isNotZero() } <= 1) numerator.toReversedString() else "(" + numerator.toReversedString() + ")") +
                        " / " +
                        (if (denominator.coefficients.count { it.isNotZero() } <= 1) denominator.toReversedString() else "(" + denominator.toReversedString() + ")")
        }

    fun removeZeros() =
        UnivariateRationalFunction(
            numerator.removeZeros(),
            denominator.removeZeros()
        )

    fun toRationalFunction() = RationalFunction(numerator.toPolynomial(), denominator.toPolynomial())

    fun toLabeledRationalFunction() = LabeledRationalFunction(numerator.toLabeledPolynomial(), denominator.toLabeledPolynomial())
}