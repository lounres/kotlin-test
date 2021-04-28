package math.ringsAndFields


// (a - b) mod c
private fun differenceModulo(a: Integer, b: Integer, c: Integer): Integer = (a - b) % c

private fun differenceModulo(a: Rational, b: Rational, c: Rational): Rational = (a - b) % c

/**
 * Calculates the final element of a bounded arithmetic progression, i.e. the last element of the progression which is in the range
 * from [start] to [end] in case of a positive [step], or from [end] to [start] in case of a negative
 * [step].
 *
 * No validation on passed parameters is performed. The given parameters should satisfy the condition:
 *
 * - either `step > 0` and `start <= end`,
 * - or `step < 0` and `start >= end`.
 *
 * @param start first element of the progression
 * @param end ending bound for the progression
 * @param step increment, or difference of successive elements in the progression
 * @return the final element of the progression
 * @suppress
 */
//@PublishedApi
internal fun getProgressionLastElement(start: Integer, end: Integer, step: Integer): Integer = when {
    step > 0 -> if (start >= end) end else end - differenceModulo(end, start, step)
    step < 0 -> if (start <= end) end else end + differenceModulo(start, end, -step)
    else -> throw kotlin.IllegalArgumentException("Step is zero.")
}

/**
 * Calculates the final element of a bounded arithmetic progression, i.e. the last element of the progression which is in the range
 * from [start] to [end] in case of a positive [step], or from [end] to [start] in case of a negative
 * [step].
 *
 * No validation on passed parameters is performed. The given parameters should satisfy the condition:
 *
 * - either `step > 0` and `start <= end`,
 * - or `step < 0` and `start >= end`.
 *
 * @param start first element of the progression
 * @param end ending bound for the progression
 * @param step increment, or difference of successive elements in the progression
 * @return the final element of the progression
 * @suppress
 */
//@PublishedApi
internal fun getProgressionLastElement(start: Rational, end: Rational, step: Rational): Rational = when {
    step > 0 -> if (start >= end) end else end - differenceModulo(end, start, step)
    step < 0 -> if (start <= end) end else end + differenceModulo(start, end, -step)
    else -> throw kotlin.IllegalArgumentException("Step is zero.")
}