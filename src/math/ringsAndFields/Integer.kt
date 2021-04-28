package math.ringsAndFields

import java.math.BigInteger


class Integer (val value: BigInteger): Ring<Integer>, Comparable<Integer> {
    constructor(value: Int) : this(value.toBigInteger())
    constructor(value: Long) : this(value.toBigInteger())
    constructor(str: String) : this(str.toBigInteger())

    override fun getZero(): Integer = ZERO
    override fun getOne(): Integer = ONE
    override fun isZero(): Boolean = value == BigInteger.ZERO
    override fun isOne(): Boolean = value == BigInteger.ONE

    companion object {
        val ZERO = Integer(BigInteger.ZERO)
        val ONE = Integer(BigInteger.ONE)

        data class DividingResult(
            val quotient: Integer,
            val reminder: Integer
        )
    }

    override fun unaryPlus() = this

    override fun unaryMinus() = Integer(-value)

    override operator fun plus(other: Integer): Integer = Integer(value + other.value)

    override operator fun plus(other: Int): Integer = Integer(value + other.toBigInteger())

    override operator fun plus(other: Long): Integer = Integer(value + other.toBigInteger())

    operator fun plus(other: Rational): Rational =
        Rational(
            this * other.denominator + other.numerator,
            other.denominator
        )

    override operator fun minus(other: Integer): Integer = Integer(value - other.value)

    override operator fun minus(other: Int): Integer = Integer(value - other.toBigInteger())

    override operator fun minus(other: Long): Integer = Integer(value - other.toBigInteger())

    operator fun minus(other: Rational): Rational =
        Rational(
            this * other.denominator - other.numerator,
            other.denominator
        )

    override operator fun times(other: Integer): Integer = Integer(value * other.value)

    override operator fun times(other: Int): Integer = Integer(value * other.toBigInteger())

    override operator fun times(other: Long): Integer = Integer(value * other.toBigInteger())

    operator fun times(other: Rational): Rational =
        Rational(
            this * other.numerator,
            other.denominator
        )

    operator fun div(other: Integer): Integer = (this divrem other).quotient

    operator fun div(other: Int): Integer = this / other.toInteger()

    operator fun div(other: Long): Integer = this / other.toInteger()

    operator fun div(other: Rational): Rational =
        Rational(
            this * other.denominator,
            other.numerator
        )

    operator fun rem(other: Integer): Integer = (this divrem other).reminder

    operator fun rem(other: Int): Integer = this % other.toInteger()

    operator fun rem(other: Long): Integer = this % other.toInteger()

    operator fun rem(other: Rational): Rational = toRational() % other

    infix fun divrem(other: Integer) : DividingResult {
        var (quotient, reminder) = value.abs().divideAndRemainder(other.value.abs()).let { it[0] to it[1] }
        if (value.signum() == -1) {
            quotient = -quotient
            if (reminder != BigInteger.ZERO) {
                quotient -= BigInteger.ONE
                reminder = other.value.abs() - reminder
            }
        }
        if (other.value.signum() == -1) {
            quotient = -quotient
        }
        return DividingResult(Integer(quotient), Integer(reminder))
    }

    operator fun inc() = Integer(value + BigInteger.ONE)

    operator fun dec() = Integer(value - BigInteger.ONE)

    infix fun and(other: Integer) = Integer(value and other.value)

    infix fun or(other: Integer) = Integer(value or other.value)

    infix fun xor(other: Integer) = Integer(value xor other.value)

    infix fun shl(other: Int) = Integer(value shl other)

    infix fun shr(other: Int) = Integer(value shr other)

    override fun equals(other: Any?): Boolean =
        when (other) {
            is Integer -> value == other.value
            is Rational -> other.numerator == this && other.denominator == ONE
            is Int -> value == other.toBigInteger()
            is Long -> value == other.toBigInteger()
            else -> false
        }

    operator fun compareTo(other: Rational): Int = (this * other.denominator).compareTo(other.numerator)

    override operator fun compareTo(other: Integer): Int = value.compareTo(other.value)

    operator fun compareTo(other: Int): Int = value.compareTo(other.toBigInteger())

    operator fun compareTo(other: Long): Int = value.compareTo(other.toBigInteger())

    override fun hashCode(): Int = value.hashCode()

    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Integer) = IntegerRange(this, other)
    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Rational) = ClosedRationalRange(this.toRational(), other)
    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Int) = IntegerRange(this, other.toInteger())
    /** Creates a range from this value to the specified [other] value. */
    operator fun rangeTo(other: Long) = IntegerRange(this, other.toInteger())

    fun toRational(): Rational = Rational(this)

    fun toInteger(): Integer = this

    fun toInt(): Int = value.toInt()

    fun toLong(): Long = value.toLong()

    fun toFloat(): Float = value.toFloat()

    fun toDouble(): Double = value.toDouble()

    override fun toString(): String = "$value"
}
