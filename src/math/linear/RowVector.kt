package math.linear

import math.ringsAndFields.*


class RowVector<T: Ring<T>> internal constructor(
    countOfColumns: Int,
    coefficients: List<List<T>>,
    toCheckInput: Boolean = true
): Matrix<T>(1, countOfColumns, coefficients, toCheckInput) {
    override val rank: Int by lazy { if (coefficients.first().any { it.isNotZero() }) 1 else 0 }
    val rawCoefficients
        get() = coefficients.first()

    internal constructor(coefficients: List<List<T>>, toCheckInput: Boolean = true) : this(
        with(coefficients) { when {
            isEmpty() -> throw IllegalArgumentException("Count of rows must be positive")
            else -> first().size
        } },
        coefficients,
        toCheckInput = toCheckInput
    )
    internal constructor(vararg coefficients: T, toCheckInput: Boolean) : this(listOf(coefficients.toList()), toCheckInput)
    constructor(countOfColumns: Int, coefficients: List<List<T>>) : this(
        countOfColumns,
        coefficients,
        toCheckInput = true
    )
    constructor(coefficients: List<List<T>>) : this(
        with(coefficients) { when {
            isEmpty() -> throw IllegalArgumentException("Count of rows must be positive")
            else -> first().size
        } },
        coefficients,
        toCheckInput = true
    )
    constructor(vararg coefficients: T) : this(listOf(coefficients.toList()))
    constructor(countOfColumns: Int, init: (rowIndex: Int, columnIndex: Int) -> T) : this(
        countOfColumns.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        listOf(List(countOfColumns) { columnIndex -> init(0, columnIndex) }),
        toCheckInput = false
    )
    constructor(countOfColumns: Int, init: (columnIndex: Int) -> T) : this(
        countOfColumns.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        listOf(List(countOfColumns) { columnIndex -> init(columnIndex) }),
        toCheckInput = false
    )

    /*override*/ operator fun plus(other: RowVector<T>): RowVector<T> =
        if (countOfColumns != other.countOfColumns) throw IllegalArgumentException("Can not add two row vectors of different sizes")
        else
            RowVector(
                countOfColumns,
                listOf(
                    coefficients[0].mapIndexed { columnIndex, t -> t + other.coefficients[0][columnIndex] }
                ),
                toCheckInput = false
            )

    /*override*/ operator fun minus(other: RowVector<T>): RowVector<T> =
        if (countOfColumns != other.countOfColumns) throw IllegalArgumentException("Can not subtract two row vectors of different sizes")
        else
            RowVector(
                countOfColumns,
                listOf(
                    coefficients[0].mapIndexed { columnIndex, t -> t - other.coefficients[0][columnIndex] }
                ),
                toCheckInput = false
            )

    /*override*/ operator fun times(other: SquareMatrix<T>): RowVector<T> =
        if (countOfColumns != other.countOfRows) throw IllegalArgumentException("Can not multiply row vector and square matrix with not matching sizes")
        else
            RowVector(
                countOfColumns,
                listOf(
                    List(other.countOfColumns) { columnIndex ->
                        coefficients[0]
                            .asSequence()
                            .mapIndexed { index, t -> t * other.coefficients[index][columnIndex] }
                            .reduce { acc, t -> acc + t }
                    }
                ),
                toCheckInput = false
            )

    override operator fun times(other: T): RowVector<T> =
        RowVector(countOfRows) { index -> coefficients[0][index] * other }

    override operator fun times(other: Integer): RowVector<T> =
        RowVector(countOfRows) { index -> coefficients[0][index] * other }

    override operator fun times(other: Int): RowVector<T> =
        RowVector(countOfRows) { index -> coefficients[0][index] * other }

    override operator fun times(other: Long): RowVector<T> =
        RowVector(countOfRows) { index -> coefficients[0][index] * other }

    operator fun get(columnIndex: Int) : T =
        if (columnIndex !in 0 until countOfColumns) throw Companion.MatrixIndexOutOfBoundsException("Column index out of bounds: $columnIndex got, in 0..${countOfColumns - 1} expected")
        else coefficients[0][columnIndex]

    override fun transposed(): ColumnVector<T> =
        ColumnVector(
            countOfColumns,
            List(countOfColumns) { rowIndex -> listOf(coefficients[0][rowIndex]) },
            toCheckInput = false
        )
}