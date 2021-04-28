package math.varia

import math.ringsAndFields.ComplexOverDouble
import kotlin.math.*


typealias Complex = ComplexOverDouble

var logLimit = 3
    set(value) {
        field = value
        limit = 1 shl logLimit
    }
var limit = 1 shl logLimit

lateinit var rev: MutableList<Int>
fun calcRev () {
    rev = MutableList(limit) { 0 }
    for(i in 0 until limit)
        for(k in 0 until logLimit)
            if (i and (1 shl k) != 0)
                rev[i] = rev[i] or (1 shl (logLimit - k - 1))
}

var forwardZ = MutableList(0) { Complex.ZERO }
var backwardZ = MutableList(0) { Complex.ZERO }

fun calcZ () {
    forwardZ = MutableList(limit) {
        Complex(
            cos(it * 2 * PI / limit),
            sin(it * 2 * PI / limit)
        )
    }
    backwardZ = MutableList(limit) { forwardZ[(limit - it) % limit] }
}

fun FFT (a0 : MutableList<Complex>, inv: Boolean = false) : MutableList<Complex> {
    val a = a0.toMutableList()
    for(i in 0 until limit)
        if(i < rev[i])
            a[i] = a[rev[i]].also { a[rev[i]] = a[i] }
    val curZ = if(inv) backwardZ else forwardZ
    var k = 0
    var span = 1
    var step = limit / 2
    while(k < logLimit) {
        for(i in 0 until limit step 2 * span)
            for(j in 0 until span) {
                val u = i + j
                val v = i + j + span
                val x = a[u] + a[v] * curZ[j * step]
                val y = a[u] + a[v] * curZ[j * step + limit / 2]
                a[u] = x
                a[v] = y
            }
        k++
        span *= 2
        step /= 2
    }
    if(inv) for(i in 0 until limit) a[i] = a[i] / limit
    return a
}

fun main() {
    val input = readLine()!!
    val m = input.length
    logLimit = ceil(log2(m.toDouble())).toInt() + 1
    calcRev()
    calcZ()
    var a = MutableList(limit) { Complex.ZERO }
    for(i in 0 until m) {
        if (input[i] == '1') {
            a[i] = Complex(1)
        }
    }
    val aFFT = FFT(a)
    for(i in 0 until limit) aFFT[i] = aFFT[i] * aFFT[i]
    a = FFT(aFFT, true)
    var res = 0
    for(i in 0 until m) {
        if (input[i] == '1') {
            res += round(a[2 * i].real).toInt() - 1
        }
    }
    println(res / 2)
}