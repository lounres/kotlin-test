package math.varia

import kotlin.properties.Delegates

data class Result(
    val depth: Int,
    val digits: List<Int>,
    val history: List<Int>,
    val score: Int,
    val penalty: Int
) {
    constructor() : this(
        0,
        start,
        emptyList(),
        0,
        0
    )

    fun nextStep(index: Int): Result =
        Result(
            depth + 1,
            digits
                .toMutableList()
                .apply {
                    this[index] = bank[depth * 2]
                    this[(index + 1) % K] = bank[depth * 2 + 1]
                },
            history + listOf(index),
            score + digits[index] * digits[(index + 1) % K],
            penalty + (digits[index] - digits[(index + 1) % K]).let { it * it }
        )
}

val K = 10
var M by Delegates.notNull<Int>()
val W = 2
lateinit var start: List<Int>
lateinit var bank: List<Int>


fun main() {
    val input = readLine()!!.map { it.toString().toInt() }
    start = input.subList(0, K)
    bank = input.subList(K, input.size)
    M = (input.size - 10) / 2

    var results = listOf(
        Result(
            0,
            start,
            emptyList(),
            0,
            0
        )
    )

    for (i in 0 until M)
        results =
            results
                .flatMap { List(K) { index -> it.nextStep(index) } }
                .sortedBy { it.penalty }
                .take(W)

    val totalResult = results.maxByOrNull { it.score } !!

    for (index in totalResult.history) println("$index${(index + 1) % K}")
    println(totalResult.score)

//    var r = Result()
//    println(r)
//    r = r.nextStep(4)
//    println(r)
//    r = r.nextStep(6)
//    println(r)
//    r = r.nextStep(4)
//    println(r)
//    r = r.nextStep(4)
//    println(r)
//    r = r.nextStep(5)
//    println(r)
}