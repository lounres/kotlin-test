package math.ringsAndFields


/**
 * Returns `true` if this range contains the specified [element].
 *
 * Always returns `false` if the [element] is `null`.
 */
operator fun IntegerRange.contains(element: Integer?): Boolean {
    return element != null && contains(element)
}

/**
 * Returns `true` if this range contains the specified [element].
 *
 * Always returns `false` if the [element] is `null`.
 */
operator fun ClosedRationalRange.contains(element: Rational?): Boolean {
    return element != null && contains(element)
}

/**
 * Checks if the specified [value] belongs to this range.
 */
@kotlin.jvm.JvmName("intRangeContains")
operator fun ClosedRange<Int>.contains(value: Integer): Boolean {
    return value.toIntExactOrNull().let { if (it != null) contains(it) else false }
}

/**
 * Checks if the specified [value] belongs to this range.
 */
@kotlin.jvm.JvmName("longRangeContains")
operator fun ClosedRange<Long>.contains(value: Integer): Boolean {
    return value.toLongExactOrNull().let { if (it != null) contains(it) else false }
}

/**
 * Checks if the specified [value] belongs to this range.
 */
@kotlin.jvm.JvmName("integerRangeContains")
operator fun ClosedRange<Integer>.contains(value: Int): Boolean {
    return contains(value.toInteger())
}

/**
 * Checks if the specified [value] belongs to this range.
 */
@kotlin.jvm.JvmName("integerRangeContains")
operator fun ClosedRange<Integer>.contains(value: Long): Boolean {
    return contains(value.toInteger())
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Integer.downTo(to: Integer): IntegerProgression {
    return IntegerProgression.fromClosedRange(this, to, -Integer.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Integer.downTo(to: Rational): RationalProgression {
    return RationalProgression.fromClosedRange(this.toRational(), to, -Rational.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Integer.downTo(to: Int): IntegerProgression {
    return IntegerProgression.fromClosedRange(this, to.toInteger(), -Integer.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Integer.downTo(to: Long): IntegerProgression {
    return IntegerProgression.fromClosedRange(this, to.toInteger(), -Integer.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Rational.downTo(to: Rational): RationalProgression {
    return RationalProgression.fromClosedRange(this, to, -Rational.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Rational.downTo(to: Integer): RationalProgression {
    return RationalProgression.fromClosedRange(this, to.toRational(), -Rational.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Rational.downTo(to: Int): RationalProgression {
    return RationalProgression.fromClosedRange(this, to.toRational(), -Rational.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Rational.downTo(to: Long): RationalProgression {
    return RationalProgression.fromClosedRange(this, to.toRational(), -Rational.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Int.downTo(to: Integer): IntegerProgression {
    return IntegerProgression.fromClosedRange(this.toInteger(), to, -Integer.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Long.downTo(to: Integer): IntegerProgression {
    return IntegerProgression.fromClosedRange(this.toInteger(), to, -Integer.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Int.downTo(to: Rational): RationalProgression {
    return RationalProgression.fromClosedRange(this.toRational(), to, -Rational.ONE)
}

/**
 * Returns a progression from this value down to the specified [to] value with the step -1.
 *
 * The [to] value should be less than or equal to `this` value.
 * If the [to] value is greater than `this` value the returned progression is empty.
 */
infix fun Long.downTo(to: Rational): RationalProgression {
    return RationalProgression.fromClosedRange(this.toRational(), to, -Rational.ONE)
}

/**
 * Returns a progression that goes over the same range in the opposite direction with the same step.
 */
fun IntegerProgression.reversed(): IntegerProgression {
    return IntegerProgression.fromClosedRange(last, first, -step)
}

/**
 * Returns a progression that goes over the same range in the opposite direction with the same step.
 */
fun RationalProgression.reversed(): RationalProgression {
    return RationalProgression.fromClosedRange(last, first, -step)
}

internal fun checkStepIsPositive(isPositive: Boolean, step: Any?) {
    if (!isPositive) throw IllegalArgumentException("Step must be positive, was: $step.")
}

/**
 * Returns a progression that goes over the same range with the given step.
 */
infix fun IntegerProgression.step(step: Integer): IntegerProgression {
    checkStepIsPositive(step > 0, step)
    return IntegerProgression.fromClosedRange(first, last, if (this.step > 0) step else -step)
}

/**
 * Returns a progression that goes over the same range with the given step.
 */
infix fun ClosedRationalRange.step(step: Rational): RationalProgression {
    return RationalProgression.fromClosedRange(start, endInclusive, step)
}

internal fun Integer.toIntExactOrNull(): Int? {
    return if (this in Int.MIN_VALUE.toInteger()..Int.MAX_VALUE.toInteger()) this.toInt() else null
}

internal fun Rational.toIntExactOrNull(): Int? {
    return if (this in Int.MIN_VALUE.toRational()..Int.MAX_VALUE.toRational()) this.toInt() else null
}

internal fun Integer.toLongExactOrNull(): Int? {
    return if (this in Long.MIN_VALUE.toInteger()..Long.MAX_VALUE.toInteger()) this.toInt() else null
}

internal fun Rational.toLongExactOrNull(): Int? {
    return if (this in Long.MIN_VALUE.toRational()..Long.MAX_VALUE.toRational()) this.toInt() else null
}

/**
 * Returns a range from this value up to but excluding the specified [to] value.
 *
 * If the [to] value is less than or equal to `this` value, then the returned range is empty.
 */
infix fun Integer.until(to: Integer): IntegerRange {
    return this .. (to - Integer.ONE)
}

/**
 * Returns a range from this value up to but excluding the specified [to] value.
 *
 * If the [to] value is less than or equal to `this` value, then the returned range is empty.
 */
infix fun Integer.until(to: Int): IntegerRange {
    return this .. (to.toInteger() - Integer.ONE)
}

/**
 * Returns a range from this value up to but excluding the specified [to] value.
 *
 * If the [to] value is less than or equal to `this` value, then the returned range is empty.
 */
infix fun Integer.until(to: Long): IntegerRange {
    return this .. (to.toInteger() - Integer.ONE)
}

/**
 * Returns a range from this value up to but excluding the specified [to] value.
 *
 * If the [to] value is less than or equal to `this` value, then the returned range is empty.
 */
infix fun Int.until(to: Integer): IntegerRange {
    return this.toInteger() .. (to - Integer.ONE)
}

/**
 * Returns a range from this value up to but excluding the specified [to] value.
 *
 * If the [to] value is less than or equal to `this` value, then the returned range is empty.
 */
infix fun Long.until(to: Integer): IntegerRange {
    return this.toInteger() .. (to - Integer.ONE)
}

/**
 * Ensures that this value is not less than the specified [minimumValue].
 *
 * @return this value if it's greater than or equal to the [minimumValue] or the [minimumValue] otherwise.
 */
fun Integer.coerceAtLeast(minimumValue: Integer): Integer {
    return if (this < minimumValue) minimumValue else this
}

/**
 * Ensures that this value is not less than the specified [minimumValue].
 *
 * @return this value if it's greater than or equal to the [minimumValue] or the [minimumValue] otherwise.
 */
fun Rational.coerceAtLeast(minimumValue: Rational): Rational {
    return if (this < minimumValue) minimumValue else this
}

/**
 * Ensures that this value is not greater than the specified [maximumValue].
 *
 * @return this value if it's less than or equal to the [maximumValue] or the [maximumValue] otherwise.
 */
fun Integer.coerceAtMost(maximumValue: Integer): Integer {
    return if (this > maximumValue) maximumValue else this
}

/**
 * Ensures that this value is not greater than the specified [maximumValue].
 *
 * @return this value if it's less than or equal to the [maximumValue] or the [maximumValue] otherwise.
 */
fun Rational.coerceAtMost(maximumValue: Rational): Rational {
    return if (this > maximumValue) maximumValue else this
}

/**
 * Ensures that this value lies in the specified range [minimumValue]..[maximumValue].
 *
 * @return this value if it's in the range, or [minimumValue] if this value is less than [minimumValue], or [maximumValue] if this value is greater than [maximumValue].
 */
fun Integer.coerceIn(minimumValue: Integer, maximumValue: Integer): Integer {
    if (minimumValue > maximumValue) throw IllegalArgumentException("Cannot coerce value to an empty range: maximum $maximumValue is less than minimum $minimumValue.")
    if (this < minimumValue) return minimumValue
    if (this > maximumValue) return maximumValue
    return this
}

/**
 * Ensures that this value lies in the specified range [minimumValue]..[maximumValue].
 *
 * @return this value if it's in the range, or [minimumValue] if this value is less than [minimumValue], or [maximumValue] if this value is greater than [maximumValue].
 */
fun Rational.coerceIn(minimumValue: Rational, maximumValue: Rational): Rational {
    if (minimumValue > maximumValue) throw IllegalArgumentException("Cannot coerce value to an empty range: maximum $maximumValue is less than minimum $minimumValue.")
    if (this < minimumValue) return minimumValue
    if (this > maximumValue) return maximumValue
    return this
}

/**
 * Ensures that this value lies in the specified [range].
 *
 * @return this value if it's in the [range], or `range.start` if this value is less than `range.start`, or `range.endInclusive` if this value is greater than `range.endInclusive`.
 */
fun Integer.coerceIn(range: ClosedRange<Integer>): Integer {
    if (range is ClosedFloatingPointRange) {
        return this.coerceIn<Integer>(range)
    }
    if (range.isEmpty()) throw IllegalArgumentException("Cannot coerce value to an empty range: $range.")
    return when {
        this < range.start -> range.start
        this > range.endInclusive -> range.endInclusive
        else -> this
    }
}

/**
 * Ensures that this value lies in the specified [range].
 *
 * @return this value if it's in the [range], or `range.start` if this value is less than `range.start`, or `range.endInclusive` if this value is greater than `range.endInclusive`.
 */
fun Rational.coerceIn(range: ClosedRange<Rational>): Rational {
    if (range is ClosedFloatingPointRange) {
        return this.coerceIn<Rational>(range)
    }
    if (range.isEmpty()) throw IllegalArgumentException("Cannot coerce value to an empty range: $range.")
    return when {
        this < range.start -> range.start
        this > range.endInclusive -> range.endInclusive
        else -> this
    }
}

