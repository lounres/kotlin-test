package math.ringsAndFields

/**
 * Interface for mathematical commutative associative rings with additive and multiplicative identities and additive inversion.
 * Classes which inherit from this interface have defined addiction, subtraction and multiplication operations and additive and multiplicative identities.
 *
 * @param T Any class that is supposed to be the ring. It is supposed to use such inheritance as:
 * ```
 * class MyRing : Ring<MyRing>
 * ```
 * You can try to use any other [Ring] inheritor as a [T] parameter, but it's not recommended.
 *
 * @see Field
 */
interface Ring<T: Ring<T>> {
    /**
     * Returns the zero (the additive identity) of the ring.
     *
     * It's recommended to create static ring with the zero in inheritor and return it by the method.
     */
    fun getZero(): T
    /**
     * Returns the one (the multiplicative identity) of the ring.
     *
     * It's recommended to create static ring with the onr in inheritor and return it by the method.
     */
    fun getOne(): T
    /**
     * Checks if the instant is the zero (the additive identity) of the ring.
     */
    fun isZero(): Boolean = this == this.getZero()
    /**
     * Checks if the instant is the one (the multiplicative identity) of the ring.
     */
    fun isOne(): Boolean = this == this.getOne()
    /**
     * Checks if the instant is **not** the zero (the additive identity) of the ring.
     */
    fun isNotZero() = !isZero()
    /**
     * Checks if the instant is **not** the one (the multiplicative identity) of the ring.
     */
    fun isNotOne() = !isOne()

    /**
     * Returns the same instant of the ring.
     *
     * Should be realised like
     * ```
     * override operator fun unaryPlus(): T = this
     * ```
     * But it's possible to use other realisations.
     */
    operator fun unaryPlus(): T
    /**
     * Returns negation of the instant of the ring.
     */
    operator fun unaryMinus(): T

    /**
     * Returns sum of the instants of the ring.
     */
    operator fun plus(other: T): T
    /**
     * Returns the instants of the ring summed up with one [other] times.
     *
     * I.e. it's the same as `this + t + ... + t` where the instance `t` is one (`t = this.getOne`) and is used exactly [other] times.
     * If [other] is zero, zero is provided. If [other] is negative, minus one will be summed.
     */
    operator fun plus(other: Integer): T = this + getOne() * other
    /**
     * Returns the instants of the ring summed up with one [other] times.
     *
     * I.e. it's the same as `this + t + ... + t` where the instance `t` is one (`t = this.getOne`) and is used exactly [other] times.
     * If [other] is zero, zero is provided. If [other] is negative, minus one will be summed.
     */
    operator fun plus(other: Int): T = this + getOne() * other
    /**
     * Returns the instants of the ring summed up with one [other] times.
     *
     * I.e. it's the same as `this + t + ... + t` where the instance `t` is one (`t = this.getOne`) and is used exactly [other] times.
     * If [other] is zero, zero is provided. If [other] is negative, minus one will be summed.
     */
    operator fun plus(other: Long): T = this + getOne() * other

    /**
     * Returns difference of the instants of the ring.
     */
    operator fun minus(other: T): T
    /**
     * Returns the instants of the ring summed up with minus one [other] times.
     *
     * I.e. it's the same as `this - t - ... - t` where the instance `t` is one (`t = -this.getOne`) and is used exactly [other] times.
     * If [other] is zero, the instance is provided. If [other] is negative, one will be summed.
     */
    operator fun minus(other: Integer): T = this - getOne() * other
    /**
     * Returns the instants of the ring summed up with one [other] times.
     *
     * I.e. it's the same as `this - t - ... - t` where the instance `t` is one (`t = -this.getOne`) and is used exactly [other] times.
     * If [other] is zero, the instance is provided. If [other] is negative, one will be summed.
     */
    operator fun minus(other: Int): T = this - getOne() * other
    /**
     * Returns the instants of the ring summed up with one [other] times.
     *
     * I.e. it's the same as `this - t - ... - t` where the instance `t` is one (`t = -this.getOne`) and is used exactly [other] times.
     * If [other] is zero, the instance is provided. If [other] is negative, one will be summed.
     */
    operator fun minus(other: Long): T = this - getOne() * other

    /**
     * Returns product of the instants of the ring.
     */
    operator fun times(other: T): T
    /**
     * Returns the instants of the ring summed up with itself [other] times.
     *
     * I.e. it's the same as `this + ... + this` where the instance is used exactly [other] times.
     * If [other] is zero, zero is provided. If [other] is negative, negation will be summed.
     */
    operator fun times(other: Integer): T
    /**
     * Returns the instants of the ring summed up with itself [other] times.
     *
     * I.e. it's the same as `this + ... + this` where the instance is used exactly [other] times.
     * If [other] is zero, zero is provided. If [other] is negative, negation will be summed.
     */
    operator fun times(other: Int): T
    /**
     * Returns the instants of the ring summed up with itself [other] times.
     *
     * I.e. it's the same as `this + ... + this` where the instance is used exactly [other] times.
     * If [other] is zero, zero is provided. If [other] is negative, negation will be summed.
     */
    operator fun times(other: Long): T
}
