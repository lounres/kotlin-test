package math.linear

import math.ringsAndFields.*
import java.util.*


class SquareMatrix<T: Ring<T>> internal constructor(
    countOfRows: Int,
    coefficients: List<List<T>>,
    toCheckInput: Boolean = true
): Ring<SquareMatrix<T>>, Matrix<T>(countOfRows, countOfRows, coefficients, toCheckInput) {
    val determinant: T by lazy {
        fun computeMinor(index: Int, freeIndices: SortedSet<Int>): T {
            if (index == countOfRows - 1) return coefficients[index][freeIndices.first()]
            val restIndices = freeIndices.toSortedSet()
            return freeIndices
                .asSequence()
                .mapIndexed { count, it ->
                    val c = coefficients[index][it]
                    restIndices.remove(it)
                    val minor = computeMinor(index + 1, restIndices)
                    restIndices.add(it)
                    with(c * minor) { if (count % 2 == 0) this else -this }
                }
                .reduce { acc, t -> acc + t }
        }

        computeMinor(0, (0 until countOfRows).toSortedSet())
    }
    val det: T get() = determinant

    internal constructor(coefficients: List<List<T>>, toCheckInput: Boolean = true) : this(
        coefficients.size.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        coefficients,
        toCheckInput = toCheckInput
    )
    internal constructor(vararg coefficients: List<T>, toCheckInput: Boolean) : this(coefficients.toList(), toCheckInput)
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
    constructor(vararg coefficients: List<T>) : this(coefficients.toList())
    constructor(countOfRows: Int, init: (rowIndex: Int, columnIndex: Int) -> T) : this(
        countOfRows.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        List(countOfRows) { rowIndex -> List(countOfRows) { columnIndex -> init(rowIndex, columnIndex) } },
        toCheckInput = false
    )

//    /**
//     * Returns the zero (the additive identity) of the ring.
//     *
//     * It's recommended to create static ring with the zero in inheritor and return it by the method.
//     */
    override fun getZero(): SquareMatrix<T> = SquareMatrix(countOfRows) { _, _ -> ringZero }
//    /**
//     * Returns the one (the multiplicative identity) of the ring.
//     *
//     * It's recommended to create static ring with the onr in inheritor and return it by the method.
//     */
    override fun getOne(): SquareMatrix<T> = SquareMatrix(countOfRows) { rowIndex, columnIndex -> if(rowIndex == columnIndex) ringOne else ringZero }
//    /**
//     * Checks if the instant is the zero (the additive identity) of the ring.
//     */
    override fun isZero(): Boolean = all { it.isZero() }
//    /**
//     * Checks if the instant is the one (the multiplicative identity) of the ring.
//     */
    override fun isOne(): Boolean = allIndexed { rowIndex, columnIndex, t -> if (rowIndex == columnIndex) t.isOne() else t.isZero() }

    override operator fun unaryPlus(): SquareMatrix<T> = this

    override operator fun unaryMinus(): SquareMatrix<T> =
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> -coefficients[rowIndex][columnIndex] }

    override operator fun plus(other: SquareMatrix<T>): SquareMatrix<T> =
        if (countOfRows != other.countOfRows) throw IllegalArgumentException("Can not add two square matrices of different sizes")
        else
            SquareMatrix(
                countOfRows,
                coefficients
                    .mapIndexed { rowIndex, row ->
                        val row2 = other.coefficients[rowIndex]
                        row
                            .mapIndexed { columnIndex, t -> t + row2[columnIndex] }
                    },
                toCheckInput = false
            )

    override operator fun minus(other: SquareMatrix<T>): SquareMatrix<T> =
        if (countOfRows != other.countOfRows) throw IllegalArgumentException("Can not subtract two square matrices of different sizes")
        else
            SquareMatrix(
                countOfRows,
                coefficients
                    .mapIndexed { rowIndex, row ->
                        val row2 = other.coefficients[rowIndex]
                        row
                            .mapIndexed { columnIndex, t -> t - row2[columnIndex] }
                    },
                toCheckInput = false
            )

    override operator fun times(other: SquareMatrix<T>): SquareMatrix<T> =
        if (countOfColumns != other.countOfRows) throw IllegalArgumentException("Can not multiply two square matrices of different sizes")
        else
            SquareMatrix(
                countOfRows,
                List(countOfRows) { rowIndex ->
                    List(other.countOfColumns) { columnIndex ->
                        coefficients[rowIndex]
                            .asSequence()
                            .mapIndexed { index, t -> t * other.coefficients[index][columnIndex] }
                            .reduce { acc, t -> acc + t }
                    }
                },
                toCheckInput = false
            )

    /*override*/ operator fun times(other: ColumnVector<T>): ColumnVector<T> =
        if (countOfColumns != other.countOfRows) throw IllegalArgumentException("Can not multiply square matrix and column vector with not matching sizes")
        else
            ColumnVector(
                countOfRows,
                List(countOfRows) { rowIndex ->
                    listOf(
                        coefficients[rowIndex]
                            .asSequence()
                            .mapIndexed { index, t -> t * other.coefficients[index][0] }
                            .reduce { acc, t -> acc + t }
                    )
                },
                toCheckInput = false
            )

    override operator fun times(other: T): SquareMatrix<T> =
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    override operator fun times(other: Integer): SquareMatrix<T> =
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    override operator fun times(other: Int): SquareMatrix<T> =
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    override operator fun times(other: Long): SquareMatrix<T> =
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    fun minorMatrix(rowIndex: Int, columnIndex: Int): SquareMatrix<T> =
        when {
            rowIndex !in 0 until countOfRows -> throw IndexOutOfBoundsException("Row index out of bounds: $rowIndex got, in 0..${countOfRows-1} expected")
            columnIndex !in 0 until countOfRows -> throw IndexOutOfBoundsException("Column index out of bounds: $columnIndex got, in 0..${countOfRows-1} expected")
            countOfRows == 1 -> throw IllegalArgumentException("Square matrix 1⨉1 can't provide minor matrices because of their sizes.")
            else ->
                SquareMatrix(
                    countOfRows-1,
                    coefficients
                        .toMutableList().apply { removeAt(rowIndex) }
                        .map { it.toMutableList().apply { removeAt(columnIndex) } },
                    toCheckInput = false
            )
        }

    fun minor(rowIndex: Int, columnIndex: Int): T {
        if (rowIndex !in 0 until countOfRows) throw IndexOutOfBoundsException("Row index out of bounds: $rowIndex got, in 0..${countOfRows-1} expected")
        if (columnIndex !in 0 until countOfRows) throw IndexOutOfBoundsException("Column index out of bounds: $columnIndex got, in 0..${countOfRows-1} expected")
        if (countOfRows == 1) return ringOne

        val rowIndices = (0 until countOfRows).toMutableList().apply { remove(rowIndex) }

        fun computeMinor(index: Int, freeIndices: SortedSet<Int>): T {
            if (index == countOfRows - 2) return coefficients[rowIndices[index]][freeIndices.first()]
            val restIndices = freeIndices.toSortedSet()
            return freeIndices
                .asSequence()
                .mapIndexed { count, it ->
                    val c = coefficients[rowIndices[index]][it]
                    restIndices.remove(it)
                    val minor = computeMinor(index + 1, restIndices)
                    restIndices.add(it)
                    with(c * minor) { if (count % 2 == 0) this else -this }
                }
                .reduce { acc, t -> acc + t }
        }

        return computeMinor(0, (0 until countOfRows).toSortedSet().apply { remove(columnIndex) })
    }

    fun adjugate(): SquareMatrix<T> =
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> minor(columnIndex, rowIndex).let { if (columnIndex % 2 == rowIndex % 2) it else -it } }

    override fun transposed(): SquareMatrix<T> =
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> coefficients[columnIndex][rowIndex] }
}