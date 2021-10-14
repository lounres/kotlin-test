package math

import math.ringsAndFields.*


// region Int

fun factorial(N: Int): Long =
    when {
        N < 0 -> 0
        N == 0 -> 1
        else -> factorial(N - 1) * N
    }

fun binomial(N: Int, K: Int): Int {
    if (K > N || K < 0) return 0
    if (2 * K > N) return binomial(N, N - K)

    var result = 1

    for (i in 1..K) {
        result *= N - i + 1
        result /= i
    }

    return result
}

// endregion

// region Long

fun factorial(N: Long): Long =
    when {
        N < 0L -> 0L
        N == 0L -> 1L
        else -> factorial(N - 1L) * N
    }

fun binomial(N: Long, K: Long): Long {
    if (K > N || K < 0) return 0L
    if (2L * K > N) return binomial(N, N - K)

    var result = 1L

    for (i in 1L..K) {
        result *= N - i + 1L
        result /= i
    }

    return result
}

// endregion

// region Integer

fun factorial(N: Integer): Integer =
    when {
        N < 0 -> Integer.ZERO
        N.isZero() -> Integer.ONE
        else -> factorial(N - 1) * N
    }

fun binomial(N: Integer, K: Integer): Integer {
    if (K > N || K < 0) return Integer.ZERO
    if (2 * K > N) return binomial(N, N - K)

    var result = Integer.ONE

    for (i in Integer.ONE..K) {
        result *= N - i + 1
        result /= i
    }

    return result
}

// endregion