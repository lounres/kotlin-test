package math.linear

import math.ringsAndFields.*


class ColumnVector<T: Ring<T>> internal constructor(
    countOfRows: Int,
    coefficients: List<List<T>>,
    toCheckInput: Boolean = true
): Matrix<T>(countOfRows, 1, coefficients, toCheckInput) {
    override val rank: Int by lazy { if (coefficients.any { it.first().isNotZero() }) 1 else 0 }
    val rawCoefficients
        get() = coefficients.map { it.first() }

    internal constructor(coefficients: List<List<T>>, toCheckInput: Boolean = true) : this(
        coefficients.size.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        coefficients,
        toCheckInput = toCheckInput
    )
    internal constructor(vararg coefficients: T, toCheckInput: Boolean) : this(coefficients.map { listOf(it) }, toCheckInput)
    constructor(countOfRows: Int, coefficients: List<List<T>>) : this(
        countOfRows,
        coefficients,
        toCheckInput = true
    )
    constructor(coefficients: List<List<T>>) : this(
        coefficients.size.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        coefficients,
        toCheckInput = true
    )
    constructor(vararg coefficients: T) : this(coefficients.map { listOf(it) })
    constructor(countOfRows: Int, init: (rowIndex: Int, columnIndex: Int) -> T) : this(
        countOfRows.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        List(countOfRows) { rowIndex -> listOf(init(rowIndex, 0)) },
        toCheckInput = false
    )
    constructor(countOfRows: Int, init: (rowIndex: Int) -> T) : this(
        countOfRows.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        List(countOfRows) { rowIndex -> listOf(init(rowIndex)) },
        toCheckInput = false
    )

    /*override*/ operator fun plus(other: ColumnVector<T>): ColumnVector<T> =
        if (countOfRows != other.countOfRows) throw IllegalArgumentException("Can not add two column vectors of different sizes")
        else
            ColumnVector(
                countOfRows,
                coefficients
                    .mapIndexed { rowIndex, (row) ->
                        val (row2) = other.coefficients[rowIndex]
                        listOf(row + row2)
                    },
                toCheckInput = false
            )

    /*override*/ operator fun minus(other: ColumnVector<T>): ColumnVector<T> =
        if (countOfRows != other.countOfRows) throw IllegalArgumentException("Can not multiply two column vectors of different sizes")
        else
            ColumnVector(
                countOfRows,
                coefficients
                    .mapIndexed { rowIndex, (row) ->
                        val (row2) = other.coefficients[rowIndex]
                        listOf(row - row2)
                    },
                toCheckInput = false
            )

    override operator fun times(other: T): ColumnVector<T> =
        ColumnVector(countOfRows) { index -> coefficients[index][0] * other }

    override operator fun times(other: Integer): ColumnVector<T> =
        ColumnVector(countOfRows) { index -> coefficients[index][0] * other }

    override operator fun times(other: Int): ColumnVector<T> =
        ColumnVector(countOfRows) { index -> coefficients[index][0] * other }

    override operator fun times(other: Long): ColumnVector<T> =
        ColumnVector(countOfRows) { index -> coefficients[index][0] * other }

    operator fun get(rowIndex: Int) : T =
        if (rowIndex !in 0 until countOfRows) throw Companion.MatrixIndexOutOfBoundsException("Row index out of bounds: $rowIndex got, in 0..${countOfRows - 1} expected")
        else coefficients[rowIndex][0]

    override fun transposed(): RowVector<T> =
        RowVector(countOfRows) { index -> coefficients[index][0] }
}