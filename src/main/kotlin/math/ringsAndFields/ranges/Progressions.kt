package math.ringsAndFields


/**
 * A progression of values of type `Integer`.
 *
 * @property first The first element in the progression.
 * @property last The last element in the progression.
 * @property step The step of the progression.
 */
open class IntegerProgression
internal constructor
    (
        start: Integer,
        endInclusive: Integer,
        step: Integer
    ) : Iterable<Integer> {
    init {
        if (step.isZero()) throw kotlin.IllegalArgumentException("Step must be non-zero.")
    }

    val first: Integer = start

    val last: Integer = getProgressionLastElement(start, endInclusive, step)

    val step: Integer = step

    override fun iterator(): IntegerIterator = IntegerProgressionIterator(first, last, step)

    /** Checks if the progression is empty. */
    open fun isEmpty(): Boolean = if (step > 0) first > last else first < last

    override fun equals(other: Any?): Boolean =
        other is IntegerProgression && (isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last && step == other.step)

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * (31 * first.hashCode() + last.hashCode()) + step.hashCode())

    override fun toString(): String = if (step > 0) "$first..$last step $step" else "$first downTo $last step ${-step}"

    companion object {
        /**
         * Creates IntProgression within the specified bounds of a closed range.

         * The progression starts with the [rangeStart] value and goes toward the [rangeEnd] value not excluding it, with the specified [step].
         * In order to go backwards the [step] must be negative.
         *
         * [step] must be greater than `Int.MIN_VALUE` and not equal to zero.
         */
        fun fromClosedRange(rangeStart: Integer, rangeEnd: Integer, step: Integer): IntegerProgression = IntegerProgression(rangeStart, rangeEnd, step)
    }
}

/**
 * A progression of values of type `Rational`.
 */
open class RationalProgression
internal constructor
    (
        start: Rational,
        endInclusive: Rational,
        step: Rational
    ) : Iterable<Rational> {
    init {
        if (step.isZero()) throw kotlin.IllegalArgumentException("Step must be non-zero.")
    }

    /**
     * The first element in the progression.
     */
    val first: Rational = start

    /**
     * The last element in the progression.
     */
    val last: Rational = getProgressionLastElement(start, endInclusive, step)

    /**
     * The step of the progression.
     */
    val step: Rational = step

    override fun iterator(): RationalIterator = RationalProgressionIterator(first, last, step)

    /** Checks if the progression is empty. */
    open fun isEmpty(): Boolean = if (step > 0) first > last else first < last

    override fun equals(other: Any?): Boolean =
        other is RationalProgression && (isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last && step == other.step)

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * (31 * first.hashCode() + last.hashCode()) + step.hashCode())

    override fun toString(): String = "$first..$last step $step"

    companion object {
        /**
         * Creates IntProgression within the specified bounds of a closed range.

         * The progression starts with the [rangeStart] value and goes toward the [rangeEnd] value not excluding it, with the specified [step].
         * In order to go backwards the [step] must be negative.
         *
         * [step] must be greater than `Int.MIN_VALUE` and not equal to zero.
         */
        fun fromClosedRange(rangeStart: Rational, rangeEnd: Rational, step: Rational): RationalProgression = RationalProgression(rangeStart, rangeEnd, step)
    }
}