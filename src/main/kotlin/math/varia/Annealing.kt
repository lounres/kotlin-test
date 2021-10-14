package math.varia

import kotlin.math.*
import kotlin.properties.Delegates
import kotlin.random.Random


data class Path (
    val vertices: List<Int>,
    val length: Double
) {
    constructor(vertices: List<Int>) : this(
        vertices.toMutableList(),
        (0 until N-1).fold(.0) { acc, i: Int -> acc + distance[vertices[i] to vertices[i+1]]!! }
    )

    fun apply(change: PathChange): Path {
        val (from, to) = change

        var newLength = length
        if (from != 0) newLength =
            newLength -
                    distance[vertices[from-1] to vertices[from]]!! +
                    distance[vertices[from-1] to vertices[to-1]]!!
        if (to != N) newLength =
            newLength -
                    distance[vertices[to-1] to vertices[to]]!! +
                    distance[vertices[from] to vertices[to]]!!

        val s = from + to - 1
        val newVertices = vertices.toMutableList()
        for (left in from until s.let { (it + it % 2) / 2 })
            newVertices[left] = newVertices[s - left].also { newVertices[s - left] = newVertices[left] }

        return Path(newVertices, newLength)
    }

    operator fun compareTo(other: Path): Int = length.compareTo(other.length)

    companion object {
        fun random(): Path = Path((0 until N).shuffled())
    }
}

data class PathChange(
    val from: Int,
    val to: Int
) {
    companion object {
        fun random(): PathChange =
            (0 until N-1)
                .random()
                .let {
                    PathChange(
                        it,
                        (it+2 .. N).random()
                    )
                }
    }
}

var N by Delegates.notNull<Int>()
lateinit var coordinates: List<Pair<Int, Int>>
lateinit var distance: Map<Pair<Int, Int>, Double>

// Random Walk
//const val steps = 1000000

// Hill Climbing
//const val steps = 1000000

// Threshold Accepting
//var averageScoreDelta by Delegates.notNull<Double>()
//var tMax by Delegates.notNull<Double>()
//var tMin by Delegates.notNull<Double>()
//var tStep by Delegates.notNull<Double>()

// Simulated Annealing
//var averageScoreDelta by Delegates.notNull<Double>()
//var tMax by Delegates.notNull<Double>()
//var tMin by Delegates.notNull<Double>()
//const val tMult = 0.9999

fun main() {
    N = readLine()!!.toInt()
    coordinates = List(N) { readLine()!!.split(" ").map { it.toInt() }.let { it[0] to it[1] } }
    distance =
        mutableMapOf<Pair<Int, Int>, Double>()
            .apply {
                for (i in 0 until N) {
                    val iPoint = coordinates[i]
                    for (j in 0 until i) {
                        val jPoint = coordinates[j]
                        val dist = sqrt(((iPoint.first - jPoint.first).let { it * it } + (iPoint.second - jPoint.second).let { it * it }).toDouble())
                        this[i to j] = dist
                        this[j to i] = dist
                    }
                }
            }

    var p = Path.random()
    var pBest = p

//    Random Walk
//    for (step in 0 until steps) {
//        p = p.apply(PathChange.random())
//        if (pBest > p) pBest = p
//    }

//    Hill Climbing
//    for (step in 0 .. steps) {
//        val pOld = p
//        p = p.apply(PathChange.random())
//        if (p > pOld) p = pOld
//        else if (pBest > p) pBest = p
//    }

//    Threshold Accepting
//    averageScoreDelta = 0.0
//    for (i in 0 until N-2) for (j in i+1 until N-1) for (k in j+1 until N) {
//        val ijdist = distance[i to j]!!
//        val jkdist = distance[j to k]!!
//        val kidist = distance[k to i]!!
//        averageScoreDelta +=
//            abs(ijdist - jkdist) +
//                    abs(jkdist - kidist) +
//                    abs(kidist - ijdist)
//    }
//    averageScoreDelta /= N * (N - 1) * (N - 2) / 6
//    tMax = 2.0 * averageScoreDelta
//    tMin = 0.0 * averageScoreDelta
//    tStep = 0.00001 * averageScoreDelta
//
//    var t = tMax
//    while (t > tMin) {
//        val pOld = p
//        p = p.apply(PathChange.random())
//
//        if (p.length > pOld.length + t) p = pOld
//        else if (pOld > p) pBest = p
//
//        t -= tStep
//    }

//    Simulated Annealing
//    averageScoreDelta = 0.0
//    for (i in 0 until N-2) for (j in i+1 until N-1) for (k in j+1 until N) {
//        val ijdist = distance[i to j]!!
//        val jkdist = distance[j to k]!!
//        val kidist = distance[k to i]!!
//        averageScoreDelta +=
//            abs(ijdist - jkdist) +
//                    abs(jkdist - kidist) +
//                    abs(kidist - ijdist)
//    }
//    averageScoreDelta /= N * (N - 1) * (N - 2) / 6
//    tMax = 1.0 * averageScoreDelta
//    tMin = 0.1 * averageScoreDelta
//
//    var t = tMax
//    while (t >= tMin) {
//        val pOld = p
//        p = p.apply(PathChange.random())
//
//        if (Random.nextDouble(t) >= exp(pOld.length - p.length)) p = pOld
//        else if (pOld > p) pBest = p
//
//        t *= tMult
//    }

    println(pBest.vertices.map { it + 1 }.joinToString(separator = " ") { it.toString() })
}