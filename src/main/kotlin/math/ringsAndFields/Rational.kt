package math.ringsAndFields

import math.gcd

/**
 * The class represents rational numbers.
 *
 * Instances contain [numerator] and [denominator] represented as [Long].
 *
 * Also [numerator] and [denominator] are coprime and [denominator] is positive.
 */
class Rational: Field<Rational>, Comparable<Rational> {
    /**
     * Returns the zero (the additive identity) of the ring.
     */
    override fun getZero(): Rational = ZERO
    /**
     * Returns the one (the multiplicative identity) of the ring.
     */
    override fun getOne(): Rational = ONE
    /**
     * Checks if the instant is the zero (the additive identity) of the ring.
     */
    override fun isZero(): Boolean = numerator.isZero()
    /**
     * Checks if the instant is the one (the multiplicative identity) of the ring.
     */
    override fun isOne(): Boolean = numerator == denominator

    companion object {
        /**
         * Constant containing the zero (the additive identity) of the [Rational] field.
         */
        val ZERO = Rational(Integer.ZERO)
        /**
         * Constant containing the one (the multiplicative identity) of the [Rational] field.
         */
        val ONE = Rational(Integer.ONE)
    }

    /**
     * Numerator of the fraction. It's stored as non-negative coprime with [denominator] integer.
     */
    val numerator: Integer
    /**
     * Denominator of the fraction. It's stored as non-zero coprime with [numerator] integer.
     */
    val denominator: Integer

    /**
     * If [toCheckInput] is `true` before assigning values to [Rational.numerator] and [Rational.denominator] makes them coprime and makes
     * denominator positive. Otherwise, just assigns the values.
     *
     * @throws ArithmeticException If denominator is zero.
     */
    internal constructor(numerator: Integer, denominator: Integer, toCheckInput: Boolean = true) {
        if (toCheckInput) {
            if (denominator.isZero()) throw ArithmeticException("/ by zero")

            val greatestCommonDivider = gcd(numerator, denominator) * (if (denominator < 0L) -1L else 1L)

            this.numerator = numerator / greatestCommonDivider
            this.denominator = denominator / greatestCommonDivider
        } else {
            this.numerator = numerator
            this.denominator = denominator
        }
    }

    /**
     * Before assigning values to [Rational.numerator] and [Rational.denominator] makes them coprime and makes
     * denominator positive.
     *
     * @throws ArithmeticException If denominator is zero.
     */
    constructor(numerator: Integer, denominator: Integer) : this(numerator, denominator, true)
    constructor(numerator: Int, denominator: Integer) : this(numerator.toInteger(), denominator, true)
    constructor(numerator: Long, denominator: Integer) : this(numerator.toInteger(), denominator, true)
    constructor(numerator: Integer, denominator: Int) : this(numerator, denominator.toInteger(), true)
    constructor(numerator: Integer, denominator: Long) : this(numerator, denominator.toInteger(), true)
    constructor(numerator: Int, denominator: Int) : this(numerator.toInteger(), denominator.toInteger(), true)
    constructor(numerator: Int, denominator: Long) : this(numerator.toInteger(), denominator.toInteger(), true)
    constructor(numerator: Long, denominator: Int) : this(numerator.toInteger(), denominator.toInteger(), true)
    constructor(numerator: Long, denominator: Long) : this(numerator.toInteger(), denominator.toInteger(), true)
    constructor(numerator: Integer) : this(numerator, Integer.ONE, false)
    constructor(numerator: Int) : this(numerator.toInteger(), Integer.ONE, false)
    constructor(numerator: Long) : this(numerator.toInteger(), Integer.ONE, false)

    /**
     * Parses simple string to [Rational] value.
     *
     * Possible inputs:
     * - `"a"` is converted to `Rational(a)`
     * - `"a/b"` is converted to `Rational(a, b)`
     *
     * @throws NumberFormatException
     */
    constructor(str: String) {
        val result = str.split("/")
        when (result.size) {
            1 -> try {
                numerator = result[0].toInteger()
                denominator = Integer.ONE
            } catch (e: NumberFormatException) {
                throw NumberFormatException("For input string: \"$str\"")
            }

            2 -> try {
                val numerator = result[0].toInteger()
                val denominator = result[1].toInteger()

                val greatestCommonDivider = gcd(numerator, denominator) * (if (denominator < 0) -1 else 1)

                this.numerator = numerator / greatestCommonDivider
                this.denominator = denominator / greatestCommonDivider
            } catch (e: NumberFormatException) {
                throw NumberFormatException("For input string: \"$str\"")
            }

            else -> throw NumberFormatException("For input string: \"$str\"")
        }
    }

    /**
     * Returns the same instant.
     */
    override operator fun unaryPlus(): Rational = this

    /**
     * Returns negation of the instant of [Rational] field.
     */
    override operator fun unaryMinus(): Rational = Rational(-this.numerator, this.denominator)

    /**
     * Returns sum of the instants of [Rational] field.
     */
    override operator fun plus(other: Rational): Rational =
        Rational(
            numerator * other.denominator + denominator * other.numerator,
            denominator * other.denominator
        )

    /**
     * Returns sum of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun plus(other: Integer): Rational =
        Rational(
            numerator + denominator * other,
            denominator
        )

    /**
     * Returns sum of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun plus(other: Int): Rational =
        Rational(
            numerator + denominator * other,
            denominator
        )

    /**
     * Returns sum of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun plus(other: Long): Rational =
        Rational(
            numerator + denominator * other,
            denominator
        )

    /**
     * Returns difference of the instants of [Rational] field.
     */
    override operator fun minus(other: Rational): Rational =
        Rational(
            numerator * other.denominator - denominator * other.numerator,
            denominator * other.denominator
        )

    /**
     * Returns difference of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun minus(other: Integer): Rational =
        Rational(
            numerator - denominator * other,
            denominator
        )

    /**
     * Returns difference of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun minus(other: Int): Rational =
        Rational(
            numerator - denominator * other,
            denominator
        )

    /**
     * Returns difference of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun minus(other: Long): Rational =
        Rational(
            numerator - denominator * other,
            denominator
        )

    /**
     * Returns product of the instants of [Rational] field.
     */
    override operator fun times(other: Rational): Rational =
        Rational(
            numerator * other.numerator,
            denominator * other.denominator
        )

    /**
     * Returns product of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun times(other: Integer): Rational =
        Rational(
            numerator * other,
            denominator
        )

    /**
     * Returns product of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun times(other: Int): Rational =
        Rational(
            numerator * other,
            denominator
        )

    /**
     * Returns product of the instants of [Rational] field. [other] is represented as [Rational].
     */
    override operator fun times(other: Long): Rational =
        Rational(
            numerator * other,
            denominator
        )

    /**
     * Returns quotient of the instants of [Rational] field.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    override operator fun div(other: Rational): Rational =
        Rational(
            numerator * other.denominator,
            denominator * other.numerator
        )

    /**
     * Returns quotient of the instants of [Rational] field. [other] is represented as [Rational].
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: Integer): Rational =
        Rational(
            numerator,
            denominator * other
        )

    /**
     * Returns quotient of the instants of [Rational] field. [other] is represented as [Rational].
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: Int): Rational =
        Rational(
            numerator,
            denominator * other
        )

    /**
     * Returns quotient of the instants of [Rational] field. [other] is represented as [Rational].
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: Long): Rational =
        Rational(
            numerator,
            denominator * other
        )

    /**
     * Returns reminder from integral division.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun rem(other: Rational): Rational =
        Rational(
            (numerator * other.denominator) % (denominator * other.numerator),
            denominator * other.denominator
        )

    /**
     * Returns reminder from integral division.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun rem(other: Integer): Rational =
        Rational(
            numerator % denominator * other,
            denominator * other
        )

    /**
     * Returns reminder from integral division.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun rem(other: Int): Rational =
        Rational(
            numerator % denominator * other,
            denominator * other
        )

    /**
     * Returns reminder from integral division.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
        operator fun rem(other: Long): Rational =
        Rational(
            numerator % denominator * other,
            denominator * other
        )

    /**
     * Checks equality of the instance to [other].
     *
     * [Integer], [Int] and [Long] values are also checked as Rational ones.
     */
    override fun equals(other: Any?): Boolean =
        when (other) {
            is Rational -> numerator == other.numerator && denominator == other.denominator
            is Integer -> numerator == other && denominator == Integer.ONE
            is Int -> numerator == other && denominator == Integer.ONE
            is Long -> numerator == other && denominator == Integer.ONE
            else -> false
        }

    /**
     * Compares the instance to [other] as [Comparable.compareTo].
     *
     * @see Comparable.compareTo
     */
    override operator fun compareTo(other: Rational): Int = (numerator * other.denominator).compareTo(other.numerator * denominator)

    /**
     * Compares the instance to [other] as [Comparable.compareTo].
     *
     * [Integer] values are also checked as Rational ones.
     *
     * @see Comparable.compareTo
     */
    operator fun compareTo(other: Integer): Int = (numerator).compareTo(denominator * other)

    /**
     * Compares the instance to [other] as [Comparable.compareTo].
     *
     * [Int] values are also checked as Rational ones.
     *
     * @see Comparable.compareTo
     */
    operator fun compareTo(other: Int): Int = (numerator).compareTo(denominator * other)

    /**
     * Compares the instance to [other] as [Comparable.compareTo].
     *
     * [Long] values are also checked as Rational ones.
     *
     * @see Comparable.compareTo
     */
    operator fun compareTo(other: Long): Int = (numerator).compareTo(denominator * other)

    override fun hashCode(): Int = 31 * numerator.hashCode() + denominator.hashCode()

    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Integer) = ClosedRationalRange(this, other.toRational())
    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Rational) = ClosedRationalRange(this, other)
    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Int) = ClosedRationalRange(this, other.toRational())
    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Long) = ClosedRationalRange(this, other.toRational())

    fun toRational(): Rational = this

    fun toInteger(): Integer = numerator / denominator

    fun toInt(): Int = (numerator / denominator).toInt()

    fun toLong(): Long = (numerator / denominator).toLong()

    fun toDouble(): Double = (numerator.toDouble() / denominator.toDouble())

    fun toFloat(): Float = (numerator.toFloat() / denominator.toFloat())

    override fun toString(): String = if (denominator.isOne()) "$numerator" else "$numerator/$denominator"
}