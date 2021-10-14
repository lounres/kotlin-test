package mathTemplates.rationalPolinomials

import math.polynomials.*
import math.ringsAndFields.*


// region Univariate polynomials

typealias RationalUPolynomial = UnivariatePolynomial<Rational>
typealias RationalURationalFunction = UnivariateRationalFunction<Rational>

// region Int aliases
@JvmName("IntRationalUPolynomial")
fun RationalUPolynomial(
    coefficients: List<Int>,
    reverse: Boolean = false
) = RationalUPolynomial(
    coefficients.map { it.toRational() },
    reverse
)
fun RationalUPolynomial(
    vararg coefficients: Int,
    reverse: Boolean = false
) = RationalUPolynomial(
    coefficients.map { it.toRational() },
    reverse
)

@JvmName("IntRationalURationalFunction")
fun RationalURationalFunction(
    numeratorCoefficients: List<Int>,
    denominatorCoefficients: List<Int>,
    reverse: Boolean = false
) = RationalURationalFunction(
    numeratorCoefficients.map { it.toRational() },
    denominatorCoefficients.map { it.toRational() },
    reverse
)
@JvmName("IntRationalURationalFunction")
fun RationalURationalFunction(
    numeratorCoefficients: List<Int>,
    reverse: Boolean = false
) = RationalURationalFunction(
    numeratorCoefficients.map { it.toRational() },
    reverse
)
// endregion

// region Long aliases
@JvmName("LongRationalUPolynomial")
fun RationalUPolynomial(
    coefficients: List<Long>,
    reverse: Boolean = false
) = RationalUPolynomial(
    coefficients.map { it.toRational() },
    reverse
)
fun RationalUPolynomial(
    vararg coefficients: Long,
    reverse: Boolean = false
) = RationalUPolynomial(
    coefficients.map { it.toRational() },
    reverse
)

@JvmName("LongRationalURationalFunction")
fun RationalURationalFunction(
    numeratorCoefficients: List<Long>,
    denominatorCoefficients: List<Long>,
    reverse: Boolean = false
) = RationalURationalFunction(
    numeratorCoefficients.map { it.toRational() },
    denominatorCoefficients.map { it.toRational() },
    reverse
)
@JvmName("LongRationalURationalFunction")
fun RationalURationalFunction(
    numeratorCoefficients: List<Long>,
    reverse: Boolean = false
) = RationalURationalFunction(
    numeratorCoefficients.map { it.toRational() },
    reverse
)
// endregion
// endregion

// region Polynomials

typealias RationalPolynomial = Polynomial<Rational>
typealias RationalRationalFunction = RationalFunction<Rational>

// region Int aliases
@JvmName("IntRationalPolynomial")
fun RationalPolynomial(
    coefs: Map<List<Int>, Int>
): RationalPolynomial = Polynomial<Rational>(
    coefs.mapValues { it.value.toRational() }
)
@JvmName("IntRationalPolynomial")
fun RationalPolynomial(
    pairs: Collection<Pair<List<Int>, Int>>
): RationalPolynomial = Polynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)
@JvmName("IntRationalPolynomial")
fun RationalPolynomial(
    vararg pairs: Pair<List<Int>, Int>
): RationalPolynomial = Polynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)

@JvmName("IntRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Map<List<Int>, Int>,
    denominatorCoefficients: Map<List<Int>, Int>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() },
    denominatorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("IntRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Int>>,
    denominatorCoefficients: Collection<Pair<List<Int>, Int>>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() },
    denominatorCoefficients.map { (key, value) -> key to value.toRational() }
)
@JvmName("IntRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Map<List<Int>, Int>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("IntRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Int>>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() }
)
// endregion

// region Long aliases
@JvmName("LongRationalPolynomial")
fun RationalPolynomial(
    coefs: Map<List<Int>, Long>
): RationalPolynomial = Polynomial<Rational>(
    coefs.mapValues { it.value.toRational() }
)
@JvmName("LongRationalPolynomial")
fun RationalPolynomial(
    pairs: Collection<Pair<List<Int>, Long>>
): RationalPolynomial = Polynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)
@JvmName("LongRationalPolynomial")
fun RationalPolynomial(
    vararg pairs: Pair<List<Int>, Long>
): RationalPolynomial = Polynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)

@JvmName("LongRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Map<List<Int>, Long>,
    denominatorCoefficients: Map<List<Int>, Long>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() },
    denominatorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("LongRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Long>>,
    denominatorCoefficients: Collection<Pair<List<Int>, Long>>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() },
    denominatorCoefficients.map { (key, value) -> key to value.toRational() }
)
@JvmName("LongRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Map<List<Int>, Long>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("LongRationalRationalFunction")
fun RationalRationalFunction(
    numeratorCoefficients: Collection<Pair<List<Int>, Long>>
): RationalRationalFunction = RationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() }
)
// endregion
// endregion

// region Labeled Polynomials

typealias RationalLPolynomial = LabeledPolynomial<Rational>
typealias RationalLRationalFunction = LabeledRationalFunction<Rational>

// region Int aliases
@JvmName("IntRationalLPolynomial")
fun RationalLPolynomial(
    coefs: Map<Map<Variable, Int>, Int>
): RationalLPolynomial = LabeledPolynomial<Rational>(
    coefs.mapValues { it.value.toRational() }
)
@JvmName("IntRationalLPolynomial")
fun RationalLPolynomial(
    pairs: Collection<Pair<Map<Variable, Int>, Int>>
): RationalLPolynomial = LabeledPolynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)
@JvmName("IntRationalLPolynomial")
fun RationalLPolynomial(
    vararg pairs: Pair<Map<Variable, Int>, Int>
): RationalLPolynomial = LabeledPolynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)

@JvmName("IntRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Int>,
    denominatorCoefficients: Map<Map<Variable, Int>, Int>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() },
    denominatorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("IntRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Int>>,
    denominatorCoefficients: Collection<Pair<Map<Variable, Int>, Int>>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() },
    denominatorCoefficients.map { (key, value) -> key to value.toRational() }
)
@JvmName("IntRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Int>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("IntRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Int>>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() }
)
// endregion

// region Long aliases
@JvmName("LongRationalLPolynomial")
fun RationalLPolynomial(
    coefs: Map<Map<Variable, Int>, Long>
): RationalLPolynomial = LabeledPolynomial<Rational>(
    coefs.mapValues { it.value.toRational() }
)
@JvmName("LongRationalLPolynomial")
fun RationalLPolynomial(
    pairs: Collection<Pair<Map<Variable, Int>, Long>>
): RationalLPolynomial = LabeledPolynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)
@JvmName("LongRationalLPolynomial")
fun RationalLPolynomial(
    vararg pairs: Pair<Map<Variable, Int>, Long>
): RationalLPolynomial = LabeledPolynomial<Rational>(
    pairs.map { (key, value) -> key to value.toRational() }
)

@JvmName("LongRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Long>,
    denominatorCoefficients: Map<Map<Variable, Int>, Long>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() },
    denominatorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("LongRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Long>>,
    denominatorCoefficients: Collection<Pair<Map<Variable, Int>, Long>>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() },
    denominatorCoefficients.map { (key, value) -> key to value.toRational() }
)
@JvmName("LongRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Map<Map<Variable, Int>, Long>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.mapValues { it.value.toRational() }
)
@JvmName("LongRationalLRationalFunction")
fun RationalLRationalFunction(
    numeratorCoefficients: Collection<Pair<Map<Variable, Int>, Long>>
): RationalLRationalFunction = LabeledRationalFunction<Rational>(
    numeratorCoefficients.map { (key, value) -> key to value.toRational() }
)
// endregion
// endregion