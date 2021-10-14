package math.ringsAndFields

/**
 * Interface for mathematical fields.
 * Classes which inherit from this interface have defined addiction, subtraction, multiplication and division operations
 * and additive and multiplicative identities.
 *
 * @param T Any class that is supposed to be the field. It is supposed to use such inheritance as:
 * ```
 * class MyRing : Ring<MyRing>
 * ```
 * You can try to use any other [Field] inheritor as a [T] parameter, but it's not recommended.
 *
 * @see Ring
 */
interface Field<T: Field<T>>: Ring<T> {
    /**
     * Returns quotient of the instants of the field.
     *
     * @throws ArithmeticException if [other] is the zero of the field it can't be a divisor.
     */
    operator fun div(other: T): T
}