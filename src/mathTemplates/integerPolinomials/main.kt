package mathTemplates.integerPolinomials

import math.polynomials.*
import math.ringsAndFields.*


// region Univariate polynomials

typealias IntegerUPolynomial = UnivariatePolynomial<Integer>
typealias IntegerURationalFunction = UnivariateRationalFunction<Integer>

// region Int aliases
@JvmName("IntIntegerUPolynomial")
fun IntegerUPolynomial(
    coefficients: List<Int>,
    reverse: Boolean = false
) = IntegerUPolynomial(
    coefficients.map { it.toInteger() },
    reverse
)
fun IntegerUPolynomial(
    vararg coefficients: Int,
    reverse: Boolean = false
) = IntegerUPolynomial(
    coefficients.map { it.toInteger() },
    reverse
)

@JvmName("IntIntegerURationalFunction")
fun IntegerURationalFunction(
    numeratorCoefficients: List<Int>,
    denominatorCoefficients: List<Int>,
    reverse: Boolean = false
) = IntegerURationalFunction(
    numeratorCoefficients.map { it.toInteger() },
    denominatorCoefficients.map { it.toInteger() },
    reverse
)
@JvmName("IntIntegerURationalFunction")
fun IntegerURationalFunction(
    numeratorCoefficients: List<Int>,
    reverse: Boolean = false
) = IntegerURationalFunction(
    numeratorCoefficients.map { it.toInteger() },
    reverse
)
// endregion

// region Long aliases
@JvmName("LongIntegerUPolynomial")
fun IntegerUPolynomial(
    coefficients: List<Long>,
    reverse: Boolean = false
) = IntegerUPolynomial(
    coefficients.map { it.toInteger() },
    reverse
)
fun IntegerUPolynomial(
    vararg coefficients: Long,
    reverse: Boolean = false
) = IntegerUPolynomial(
    coefficients.map { it.toInteger() },
    reverse
)

@JvmName("LongIntegerURationalFunction")
fun IntegerURationalFunction(
    numeratorCoefficients: List<Long>,
    denominatorCoefficients: List<Long>,
    reverse: Boolean = false
) = IntegerURationalFunction(
    numeratorCoefficients.map { it.toInteger() },
    denominatorCoefficients.map { it.toInteger() },
    reverse
)
@JvmName("LongIntegerURationalFunction")
fun IntegerURationalFunction(
    numeratorCoefficients: List<Long>,
    reverse: Boolean = false
) = IntegerURationalFunction(
    numeratorCoefficients.map { it.toInteger() },
    reverse
)
// endregion
// endregion

// region Polynomials

typealias IntegerPolynomial = Polynomial<Integer>
typealias IntegerRationalFunction = RationalFunction<Integer>

// region Int aliases
@JvmName("IntIntegerPolynomial")
fun IntegerPolynomial(
    coefs: Map<List<Int>, Int>
): IntegerPolynomial = Polynomial<Integer>(
    coefs.mapValues { it.value.toInteger() }
)
@JvmName("IntIntegerPolynomial")
fun IntegerPolynomial(
    pairs: Collection<Pair<List<Int>, Int>>
): IntegerPolynomial = Polynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)
@JvmName("IntIntegerPolynomial")
fun IntegerPolynomial(
    vararg pairs: Pair<List<Int>, Int>
): IntegerPolynomial = Polynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)

@JvmName("IntIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Map<List<Int>, Int>,
    denominatorCoefficients: Map<List<Int>, Int>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() },
    denominatorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("IntIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Int>>,
    denominatorCoefficients: Collection<Pair<List<Int>, Int>>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() },
    denominatorCoefficients.map { (key, value) -> key to value.toInteger() }
)
@JvmName("IntIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Map<List<Int>, Int>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("IntIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Int>>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() }
)
// endregion

// region Long aliases
@JvmName("LongIntegerPolynomial")
fun IntegerPolynomial(
    coefs: Map<List<Int>, Long>
): IntegerPolynomial = Polynomial<Integer>(
    coefs.mapValues { it.value.toInteger() }
)
@JvmName("LongIntegerPolynomial")
fun IntegerPolynomial(
    pairs: Collection<Pair<List<Int>, Long>>
): IntegerPolynomial = Polynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)
@JvmName("LongIntegerPolynomial")
fun IntegerPolynomial(
    vararg pairs: Pair<List<Int>, Long>
): IntegerPolynomial = Polynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)

@JvmName("LongIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Map<List<Int>, Long>,
    denominatorCoefficients: Map<List<Int>, Long>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() },
    denominatorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("LongIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Long>>,
    denominatorCoefficients: Collection<Pair<List<Int>, Long>>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() },
    denominatorCoefficients.map { (key, value) -> key to value.toInteger() }
)
@JvmName("LongIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Map<List<Int>, Long>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("LongIntegerRationalFunction")
fun IntegerRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Long>>
): IntegerRationalFunction = RationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() }
)
// endregion
// endregion

// region Labeled Polynomials

typealias IntegerLPolynomial = LabeledPolynomial<Integer>
typealias IntegerLRationalFunction = LabeledRationalFunction<Integer>

// region Int aliases
@JvmName("IntIntegerLPolynomial")
fun IntegerLPolynomial(
    coefs: Map<Map<Variable, Int>, Int>
): IntegerLPolynomial = LabeledPolynomial<Integer>(
    coefs.mapValues { it.value.toInteger() }
)
@JvmName("IntIntegerLPolynomial")
fun IntegerLPolynomial(
    pairs: Collection<Pair<Map<Variable, Int>, Int>>
): IntegerLPolynomial = LabeledPolynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)
@JvmName("IntIntegerLPolynomial")
fun IntegerLPolynomial(
    vararg pairs: Pair<Map<Variable, Int>, Int>
): IntegerLPolynomial = LabeledPolynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)

@JvmName("IntIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Int>,
    denominatorCoefficients: Map<Map<Variable, Int>, Int>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() },
    denominatorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("IntIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Int>>,
    denominatorCoefficients: Collection<Pair<Map<Variable, Int>, Int>>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() },
    denominatorCoefficients.map { (key, value) -> key to value.toInteger() }
)
@JvmName("IntIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Int>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("IntIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Int>>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() }
)
// endregion

// region Long aliases
@JvmName("LongIntegerLPolynomial")
fun IntegerLPolynomial(
    coefs: Map<Map<Variable, Int>, Long>
): IntegerLPolynomial = LabeledPolynomial<Integer>(
    coefs.mapValues { it.value.toInteger() }
)
@JvmName("LongIntegerLPolynomial")
fun IntegerLPolynomial(
    pairs: Collection<Pair<Map<Variable, Int>, Long>>
): IntegerLPolynomial = LabeledPolynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)
@JvmName("LongIntegerLPolynomial")
fun IntegerLPolynomial(
    vararg pairs: Pair<Map<Variable, Int>, Long>
): IntegerLPolynomial = LabeledPolynomial<Integer>(
    pairs.map { (key, value) -> key to value.toInteger() }
)

@JvmName("LongIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Long>,
    denominatorCoefficients: Map<Map<Variable, Int>, Long>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() },
    denominatorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("LongIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Long>>,
    denominatorCoefficients: Collection<Pair<Map<Variable, Int>, Long>>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() },
    denominatorCoefficients.map { (key, value) -> key to value.toInteger() }
)
@JvmName("LongIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Long>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.mapValues { it.value.toInteger() }
)
@JvmName("LongIntegerLRationalFunction")
fun IntegerLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Long>>
): IntegerLRationalFunction = LabeledRationalFunction<Integer>(
    numeratorCoefficients.map { (key, value) -> key to value.toInteger() }
)
// endregion
// endregion