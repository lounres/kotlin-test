package math.ringsAndFields

import kotlin.math.sqrt


class ComplexOverDouble(
    val real: Double,
    val imaginary: Double
) {
    /**
     * Returns the zero (the additive identity) of the ring.
     */
    fun getZero(): ComplexOverDouble = ZERO
    /**
     * Returns the one (the multiplicative identity) of the ring.
     */
    fun getOne(): ComplexOverDouble = ONE
    /**
     * Checks if the instant is the zero (the additive identity) of the ring.
     */
    fun isZero(): Boolean = real == .0 && imaginary == .0
    /**
     * Checks if the instant is the one (the multiplicative identity) of the ring.
     */
    fun isOne(): Boolean = real == 1.0 && imaginary == .0

    companion object {
        val ZERO = ComplexOverDouble(.0, .0)
        val ONE = ComplexOverDouble(1.0, .0)
    }

    constructor(real: Int, imaginary: Double) : this(real.toDouble(), imaginary)
    constructor(real: Long, imaginary: Double) : this(real.toDouble(), imaginary)
    constructor(real: Double, imaginary: Int) : this(real, imaginary.toDouble())
    constructor(real: Int, imaginary: Int) : this(real.toDouble(), imaginary.toDouble())
    constructor(real: Long, imaginary: Int) : this(real.toDouble(), imaginary.toDouble())
    constructor(real: Double, imaginary: Long) : this(real, imaginary.toDouble())
    constructor(real: Int, imaginary: Long) : this(real.toDouble(), imaginary.toDouble())
    constructor(real: Long, imaginary: Long) : this(real.toDouble(), imaginary.toDouble())
    constructor(real: Double) : this(real, .0)
    constructor(real: Int) : this(real.toDouble(), .0)
    constructor(real: Long) : this(real.toDouble(), .0)

    /**
     * Returns the same instant.
     */
    operator fun unaryPlus(): ComplexOverDouble = this

    /**
     * Returns negation of the instant of [ComplexOverDouble] field.
     */
    operator fun unaryMinus(): ComplexOverDouble = ComplexOverDouble(-real, -imaginary)

    /**
     * Returns sum of the instants of [ComplexOverDouble] field.
     */
    operator fun plus(other: ComplexOverDouble): ComplexOverDouble =
        ComplexOverDouble(
            real + other.real,
            imaginary + other.imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun plus(other: Double): ComplexOverDouble =
        ComplexOverDouble(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun plus(other: Int): ComplexOverDouble =
        ComplexOverDouble(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun plus(other: Long): ComplexOverDouble =
        ComplexOverDouble(
            real + other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverDouble] field.
     */
    operator fun minus(other: ComplexOverDouble): ComplexOverDouble =
        ComplexOverDouble(
            real - other.real,
            imaginary - other.imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun minus(other: Double): ComplexOverDouble =
        ComplexOverDouble(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun minus(other: Int): ComplexOverDouble =
        ComplexOverDouble(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun minus(other: Long): ComplexOverDouble =
        ComplexOverDouble(
            real - other,
            imaginary
        )

    /**
     * Returns product of the instants of [ComplexOverDouble] field.
     */
    operator fun times(other: ComplexOverDouble): ComplexOverDouble =
        ComplexOverDouble(
            real * other.real - imaginary * other.imaginary,
            real * other.imaginary + other.real * imaginary
        )

    /**
     * Returns product of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun times(other: Double): ComplexOverDouble =
        ComplexOverDouble(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun times(other: Int): ComplexOverDouble =
        ComplexOverDouble(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     */
    operator fun times(other: Long): ComplexOverDouble =
        ComplexOverDouble(
            real * other,
            imaginary * other
        )

    /**
     * Returns quotient of the instants of [ComplexOverDouble] field.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: ComplexOverDouble): ComplexOverDouble =
        with(other) {real * real + imaginary * imaginary}.let {
            ComplexOverDouble(
                (real * other.real + imaginary * other.imaginary) / it,
                (imaginary * other.real - other.imaginary * real) / it
            )
        }

    /**
     * Returns quotient of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: Double): ComplexOverDouble =
        ComplexOverDouble(
            real / other,
            imaginary / other
        )

    /**
     * Returns quotient of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: Int): ComplexOverDouble =
        ComplexOverDouble(
            real / other,
            imaginary / other
        )

    /**
     * Returns quotient of the instants of [ComplexOverDouble] field. [other] is represented as [ComplexOverDouble].
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: Long): ComplexOverDouble =
        ComplexOverDouble(
            real / other,
            imaginary / other
        )

    fun reciprocal(): ComplexOverDouble =
        (real * real + imaginary * imaginary).let {
            ComplexOverDouble(
                real / it,
                -imaginary / it
            )
        }

    fun norm(): Double = real * real + imaginary * imaginary

    fun modulus(): Double = sqrt(real * real + imaginary * imaginary)

    /**
     * Checks equality of the instance to [ComplexOverDouble].
     *
     * [Double] values are also checked as Rational ones.
     */
    override fun equals(other: Any?): Boolean =
        when {
            other === this -> true
            other is ComplexOverDouble -> real == other.real && imaginary == other.imaginary
            other is Double -> real == other && imaginary == .0
            else -> false
        }

    override fun hashCode(): Int = 31 * real.hashCode() + imaginary.hashCode()

    override fun toString(): String =
        when {
            imaginary == .0 -> "$real"
            real == .0 -> "${imaginary}i"
            else -> "$real + ${imaginary}i"
        }
}