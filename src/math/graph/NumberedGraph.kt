package math.graph

import math.ringsAndFields.*
import math.linear.*


class NumberedGraph {
    val countOfVertices: Int
    val edges: Set<Pair<Int, Int>>
    val adjacentVertices: List<Set<Int>>
    val adjacencyMatrix: SquareMatrix<Integer>

    internal constructor(
        countOfVertices: Int,
        edges: Set<Pair<Int, Int>>,
        adjacentVertices: List<Set<Int>>,
        adjacencyMatrix: SquareMatrix<Integer>,
        toCheck: Boolean = true
    ) {
        this.countOfVertices = countOfVertices
        this.edges = edges
        this.adjacentVertices = adjacentVertices
        this.adjacencyMatrix = adjacencyMatrix
//        TODO("Not yet implemented")
    }

    constructor(
        countOfVertices: Int,
        edges: Set<Pair<Int, Int>>,
        adjacentVertices: List<Set<Int>>,
        adjacencyMatrix: SquareMatrix<Integer>
    ): this(
        countOfVertices,
        edges,
        adjacentVertices,
        adjacencyMatrix,
        toCheck = true
    )

    internal constructor(countOfVertices: Int, inputEdges: Collection<Pair<Int, Int>>, toCheck: Boolean = true) {
        this.countOfVertices = countOfVertices

        edges =
            if (!toCheck) inputEdges.toSet()
            else {
                inputEdges
                    .map {
                        var (from, to) = it
                        when {
                            from !in 0 until countOfVertices -> TODO()
                            to !in 0 until countOfVertices -> TODO()
                            from == to -> TODO()
                            else -> {
                                if (from > to) from = to.also { to = from }
                                Pair(from, to)
                            }
                        }
                    }
                    .toSet()
            }

        val adjacentVerticesBuilder = List(countOfVertices) { mutableSetOf<Int>() }
        val adjacencyMatrixBuilder = List(countOfVertices) { MutableList(countOfVertices) { Integer.ZERO } }

        edges.forEach { (from, to) ->
            adjacentVerticesBuilder[from].add(to)
            adjacentVerticesBuilder[to].add(from)
            adjacencyMatrixBuilder[from][to] = Integer.ONE
            adjacencyMatrixBuilder[to][from] = Integer.ONE
        }

        adjacentVertices = adjacentVerticesBuilder
        adjacencyMatrix = SquareMatrix(countOfVertices, adjacencyMatrixBuilder)
    }
}