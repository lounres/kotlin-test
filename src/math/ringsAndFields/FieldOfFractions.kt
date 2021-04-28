package math.ringsAndFields

/**
 * TODO: documentation.
 */
open class FieldOfFractions<T: Ring<T>>(val numerator: T, val denominator: T) : Field<FieldOfFractions<T>> {
    init { if (denominator.isZero()) throw ArithmeticException("/ by zero") }

    override fun getZero(): FieldOfFractions<T> = FieldOfFractions(numerator.getZero(), numerator.getOne())
    override fun getOne(): FieldOfFractions<T> = FieldOfFractions(numerator.getZero(), numerator.getOne())
    override fun isZero(): Boolean = numerator.isZero()
    override fun isOne(): Boolean = numerator == denominator

    override operator fun unaryPlus() = this

    override operator fun unaryMinus() = FieldOfFractions(-this.numerator, this.denominator)

    override operator fun plus(other: FieldOfFractions<T>): FieldOfFractions<T> =
        FieldOfFractions(
        numerator * other.denominator + denominator * other.numerator,
        denominator * other.denominator
        )

    override operator fun minus(other: FieldOfFractions<T>): FieldOfFractions<T> =
        FieldOfFractions(
            numerator * other.denominator - denominator * other.numerator,
            denominator * other.denominator
        )

    override operator fun times(other: FieldOfFractions<T>): FieldOfFractions<T> =
        FieldOfFractions(
            numerator * other.numerator,
            denominator * other.denominator
        )

    override operator fun times(other: Integer): FieldOfFractions<T> =
        FieldOfFractions(
            numerator * other,
            denominator
        )

    override operator fun times(other: Int): FieldOfFractions<T> =
        FieldOfFractions(
            numerator * other,
            denominator
        )

    override operator fun times(other: Long): FieldOfFractions<T> =
        FieldOfFractions(
            numerator * other,
            denominator
        )

    override operator fun div(other: FieldOfFractions<T>): FieldOfFractions<T> =
        FieldOfFractions(
            numerator * other.denominator,
            denominator * other.numerator
        )

    override fun equals(other: Any?): Boolean =
        if (other !is FieldOfFractions<*>) false else numerator == other.numerator && denominator == other.denominator

    override fun hashCode(): Int = 31 * numerator.hashCode() + denominator.hashCode()
}