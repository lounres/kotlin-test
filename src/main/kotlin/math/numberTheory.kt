package math

import math.ringsAndFields.*
import kotlin.math.*

// region Integer

fun binGcd(A: Integer, B: Integer): Integer {
    var a = abs(A)
    var b = abs(B)
    while (b.isNotZero()) {
        a = b.also { b = a % b }
    }
    return a
}

fun gcd(vararg values: Integer): Integer = values.reduce(::binGcd)

fun binGcdRepr(a: Integer, b: Integer): Pair<Integer, Integer> {
    if (b.isZero()) return Pair(Integer.ONE, Integer.ZERO)
    val (p, q) = binGcdRepr(b, a % b)
    return Pair(q, p - a / b * q)
}

// endregion

// region Int

fun binGcd(A: Int, B: Int): Int {
    var a = abs(A)
    var b = abs(B)
    while (b != 0) {
        a = b.also { b = a % b }
    }
    return a
}

fun gcd(vararg values: Int): Int = values.reduce(::binGcd)

fun binGcdRepr(a: Int, b: Int): Pair<Int, Int> {
    if (b == 0) return Pair(1, 0)
    val (p, q) = binGcdRepr(b, a % b)
    return Pair(q, p - a / b * q)
}

// Checking for primality of the number
fun primality(n: Int): Boolean {
    val n = abs(n)

    if (n <= 1) return false

    val primeNumbers = MutableList(n + 1) { true }

    for (i in 2..sqrt(n.toDouble()).toInt()) {
        if (primeNumbers[i]) {
            for (j in 0..n / i) {
                primeNumbers[i * j] = false
            }
        }
    }
    return primeNumbers[n]
}

// Lagrange's sign of a and p
fun lagrangeSign(a: Int, p: Int): Int {
    if (!primality(p)) throw IllegalArgumentException("$p isn't prime")

    fun findPrimeDivider(n: Int, lower_verge: Int = 2) : Int {
        for (i in max(lower_verge, 2)..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return i
        }
        return n
    }

    var a = a % p
    if (a == 0) return 0
    if (p == 2) return 1

    var result = 1
    var primeDiv = findPrimeDivider(a)
    while (a != 1) {
        var orderOfPrime = 0
        while (a % primeDiv == 0) {
            a /= primeDiv
            orderOfPrime += 1
        }
        if (orderOfPrime % 2 == 1) {
            if (primeDiv == 2) {
                if (p % 8 !in setOf(1, 7)) result *= -1
            } else result *= lagrangeSign(p, primeDiv) * if ((p - 1) * (primeDiv - 1) / 4 % 2 == 1) -1 else 1
        }
        primeDiv = findPrimeDivider(a, primeDiv + 1)
    }
    return result
}

// endregion

// region Long

fun binGcd(A: Long, B: Long): Long {
    var a = abs(A)
    var b = abs(B)
    while (b != 0L) {
        a = b.also { b = a % b }
    }
    return a
}

fun gcd(vararg values: Long): Long = values.reduce(::binGcd)

fun binGcdRepr(a: Long, b: Long): Pair<Long, Long> {
    if (b == 0L) return Pair(1L, 0L)
    val (p, q) = binGcdRepr(b, a % b)
    return Pair(q, p - a / b * q)
}

// endregion