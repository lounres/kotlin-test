package math.ringsAndFields


/**
 * A range of values of type `Integer`.
 */
class IntegerRange(start: Integer, endInclusive: Integer) : IntegerProgression(start, endInclusive, Integer.ONE), ClosedRange<Integer> {
    override val start: Integer get() = first
    override val endInclusive: Integer get() = last

    override fun contains(value: Integer): Boolean = first <= value && value <= last

    override fun isEmpty(): Boolean = first > last

    override fun equals(other: Any?): Boolean =
        other is IntegerRange && (isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last)

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * first.hashCode() + last.hashCode())

    override fun toString(): String = "$first..$last"

    companion object {
        /** An empty range of values of type Integer. */
        val EMPTY: IntegerRange = IntegerRange(Integer.ONE, Integer.ZERO)
    }
}

/**
 * A closed range of values of type `Rational`.
 *
 * Numbers are compared with the ends of this range according to IEEE-754.
 */
class ClosedRationalRange(
    start: Rational,
    endInclusive: Rational
) : ClosedFloatingPointRange<Rational>, Iterable<Rational> {
    private val _start = start
    private val _endInclusive = endInclusive
    override val start: Rational get() = _start
    override val endInclusive: Rational get() = _endInclusive

    override fun lessThanOrEquals(a: Rational, b: Rational): Boolean = a <= b

    override fun contains(value: Rational): Boolean = value >= _start && value <= _endInclusive
    override fun isEmpty(): Boolean = _start > _endInclusive
    override fun iterator(): Iterator<Rational> = RationalProgression.fromClosedRange(start, endInclusive, Rational.ONE).iterator()

    override fun equals(other: Any?): Boolean {
        return other is ClosedRationalRange && (isEmpty() && other.isEmpty() ||
                _start == other._start && _endInclusive == other._endInclusive)
    }

    override fun hashCode(): Int {
        return if (isEmpty()) -1 else 31 * _start.hashCode() + _endInclusive.hashCode()
    }

    override fun toString(): String = "$_start..$_endInclusive"
}