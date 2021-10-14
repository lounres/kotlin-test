package math.ringsAndFields


class ComplexOverRing<T: Ring<T>>(
    val real: T,
    val imaginary: T
) : Ring<ComplexOverRing<T>> {
    /**
     * Returns the zero (the additive identity) of the ring.
     */
    override fun getZero(): ComplexOverRing<T> = real.getZero().let { ComplexOverRing(it, it) }
    /**
     * Returns the one (the multiplicative identity) of the ring.
     */
    override fun getOne(): ComplexOverRing<T> = ComplexOverRing(real.getOne(), real.getZero())
    /**
     * Checks if the instant is the zero (the additive identity) of the ring.
     */
    override fun isZero(): Boolean = real.isZero() && imaginary.isZero()
    /**
     * Checks if the instant is the one (the multiplicative identity) of the ring.
     */
    override fun isOne(): Boolean = real.isOne() && imaginary.isZero()

    constructor(real: T) : this(real, real.getZero())

    /**
     * Returns the same instant.
     */
    override operator fun unaryPlus(): ComplexOverRing<T> = this

    /**
     * Returns negation of the instant of [ComplexOverRing] field.
     */
    override operator fun unaryMinus(): ComplexOverRing<T> = ComplexOverRing(-real, -imaginary)

    /**
     * Returns sum of the instants of [ComplexOverRing] field.
     */
    override operator fun plus(other: ComplexOverRing<T>): ComplexOverRing<T> =
        ComplexOverRing(
            real + other.real,
            imaginary + other.imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    operator fun plus(other: T): ComplexOverRing<T> =
        ComplexOverRing(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun plus(other: Integer): ComplexOverRing<T> =
        ComplexOverRing(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun plus(other: Int): ComplexOverRing<T> =
        ComplexOverRing(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun plus(other: Long): ComplexOverRing<T> =
        ComplexOverRing(
            real + other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverRing] field.
     */
    override operator fun minus(other: ComplexOverRing<T>): ComplexOverRing<T> =
        ComplexOverRing(
            real - other.real,
            imaginary - other.imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    operator fun minus(other: T): ComplexOverRing<T> =
        ComplexOverRing(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun minus(other: Integer): ComplexOverRing<T> =
        ComplexOverRing(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun minus(other: Int): ComplexOverRing<T> =
        ComplexOverRing(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun minus(other: Long): ComplexOverRing<T> =
        ComplexOverRing(
            real - other,
            imaginary
        )

    /**
     * Returns product of the instants of [ComplexOverRing] field.
     */
    override operator fun times(other: ComplexOverRing<T>): ComplexOverRing<T> =
        ComplexOverRing(
            real * other.real - imaginary * other.imaginary,
            real * other.imaginary + other.real * imaginary
        )

    /**
     * Returns product of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    operator fun times(other: T): ComplexOverRing<T> =
        ComplexOverRing(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun times(other: Integer): ComplexOverRing<T> =
        ComplexOverRing(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun times(other: Int): ComplexOverRing<T> =
        ComplexOverRing(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverRing] field. [other] is represented as [ComplexOverRing].
     */
    override operator fun times(other: Long): ComplexOverRing<T> =
        ComplexOverRing(
            real * other,
            imaginary * other
        )

    fun norm(): T = real * real + imaginary * imaginary

    /**
     * Checks equality of the instance to [ComplexOverField].
     *
     * [T] values are also checked as Rational ones.
     */
    override fun equals(other: Any?): Boolean =
        when {
            other === this -> true
            other is ComplexOverRing<*> -> real == other.real && imaginary == other.imaginary
            real.javaClass.isInstance(other) -> real == other && imaginary.isZero()
            else -> false
        }

    override fun hashCode(): Int = 31 * real.hashCode() + imaginary.hashCode()

    override fun toString(): String =
        when {
            imaginary.isZero() -> "$real"
            real.isZero() -> "${imaginary}i"
            else -> "$real + ${imaginary}i"
        }
}