package math.ringsAndFields


/**
 * An iterator over a progression of values of type `Integer`.
 * @property step the number by which the value is incremented on each step.
 */
internal class IntegerProgressionIterator(first: Integer, last: Integer, val step: Integer) : IntegerIterator() {
    private val finalElement = last
    private var hasNext: Boolean = if (step > 0) first <= last else first >= last
    private var next = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun nextInteger(): Integer {
        val value = next
        if (value == finalElement) {
            if (!hasNext) throw kotlin.NoSuchElementException()
            hasNext = false
        }
        else {
            next += step
        }
        return value
    }
}

/**
 * An iterator over a progression of values of type `Rational`.
 * @property step the number by which the value is incremented on each step.
 */
internal class RationalProgressionIterator(first: Rational, last: Rational, val step: Rational) : RationalIterator() {
    private val finalElement = last
    private var hasNext: Boolean = if (step > 0) first <= last else first >= last
    private var next = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun nextRational(): Rational {
        val value = next
        if (value == finalElement) {
            if (!hasNext) throw kotlin.NoSuchElementException()
            hasNext = false
        }
        else {
            next += step
        }
        return value
    }
}