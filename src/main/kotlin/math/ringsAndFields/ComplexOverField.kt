package math.ringsAndFields


class ComplexOverField<T: Field<T>>(
    val real: T,
    val imaginary: T
) : Field<ComplexOverField<T>> {
    /**
     * Returns the zero (the additive identity) of the ring.
     */
    override fun getZero(): ComplexOverField<T> = real.getZero().let { ComplexOverField(it, it) }
    /**
     * Returns the one (the multiplicative identity) of the ring.
     */
    override fun getOne(): ComplexOverField<T> = ComplexOverField(real.getOne(), real.getZero())
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
    override operator fun unaryPlus(): ComplexOverField<T> = this

    /**
     * Returns negation of the instant of [ComplexOverField] field.
     */
    override operator fun unaryMinus(): ComplexOverField<T> = ComplexOverField(-real, -imaginary)

    /**
     * Returns sum of the instants of [ComplexOverField] field.
     */
    override operator fun plus(other: ComplexOverField<T>): ComplexOverField<T> =
        ComplexOverField(
            real + other.real,
            imaginary + other.imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    operator fun plus(other: T): ComplexOverField<T> =
        ComplexOverField(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun plus(other: Integer): ComplexOverField<T> =
        ComplexOverField(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun plus(other: Int): ComplexOverField<T> =
        ComplexOverField(
            real + other,
            imaginary
        )

    /**
     * Returns sum of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun plus(other: Long): ComplexOverField<T> =
        ComplexOverField(
            real + other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverField] field.
     */
    override operator fun minus(other: ComplexOverField<T>): ComplexOverField<T> =
        ComplexOverField(
            real - other.real,
            imaginary - other.imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    operator fun minus(other: T): ComplexOverField<T> =
        ComplexOverField(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun minus(other: Integer): ComplexOverField<T> =
        ComplexOverField(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun minus(other: Int): ComplexOverField<T> =
        ComplexOverField(
            real - other,
            imaginary
        )

    /**
     * Returns difference of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun minus(other: Long): ComplexOverField<T> =
        ComplexOverField(
            real - other,
            imaginary
        )

    /**
     * Returns product of the instants of [ComplexOverField] field.
     */
    override operator fun times(other: ComplexOverField<T>): ComplexOverField<T> =
        ComplexOverField(
            real * other.real - imaginary * other.imaginary,
            real * other.imaginary + other.real * imaginary
        )

    /**
     * Returns product of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    operator fun times(other: T): ComplexOverField<T> =
        ComplexOverField(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun times(other: Integer): ComplexOverField<T> =
        ComplexOverField(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun times(other: Int): ComplexOverField<T> =
        ComplexOverField(
            real * other,
            imaginary * other
        )

    /**
     * Returns product of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     */
    override operator fun times(other: Long): ComplexOverField<T> =
        ComplexOverField(
            real * other,
            imaginary * other
        )

    /**
     * Returns quotient of the instants of [ComplexOverField] field.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    override operator fun div(other: ComplexOverField<T>): ComplexOverField<T> =
        with(other) {real * real + imaginary * imaginary}.let {
            ComplexOverField(
                (real * other.real + imaginary * other.imaginary) / it,
                (imaginary * other.real - other.imaginary * real) / it
            )
        }

    /**
     * Returns quotient of the instants of [ComplexOverField] field. [other] is represented as [ComplexOverField].
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: T): ComplexOverField<T> =
        ComplexOverField(
            real / other,
            imaginary / other
        )

    fun reciprocal(): ComplexOverField<T> =
        (real * real + imaginary * imaginary).let {
            ComplexOverField(
                real / it,
                -imaginary / it
            )
        }

    fun norm(): T = real * real + imaginary * imaginary

    /**
     * Checks equality of the instance to [ComplexOverField].
     *
     * [T] values are also checked as Rational ones.
     */
    override fun equals(other: Any?): Boolean =
        when {
            other === this -> true
            other is ComplexOverField<*> -> real == other.real && imaginary == other.imaginary
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