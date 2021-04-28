package math.ringsAndFields


/** An iterator over a sequence of values of type `Integer`. */
abstract class IntegerIterator : Iterator<Integer> {
    final override fun next() = nextInteger()

    /** Returns the next value in the sequence without boxing. */
    abstract fun nextInteger(): Integer
}

/** An iterator over a sequence of values of type `Rational`. */
abstract class RationalIterator : Iterator<Rational> {
    final override fun next() = nextRational()

    /** Returns the next value in the sequence without boxing. */
    abstract fun nextRational(): Rational
}