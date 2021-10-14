package mathTemplates.rationalMatrices

import math.linear.*
import math.ringsAndFields.*

// region Matrix

typealias RationalMatrix = Matrix<Rational>

// region Int aliases

@JvmName("IntRationalMatrix")
fun RationalMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    coefficients: List<List<Int>>
) = RationalMatrix(
    countOfRows,
    countOfColumns,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalMatrix")
fun RationalMatrix(
    coefficients: List<List<Int>>
) = RationalMatrix(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalMatrix")
fun RationalMatrix(
    vararg coefficients: List<Int>
) = RationalMatrix(
    coefficients.toList()
)

@JvmName("IntRationalMatrix")
fun RationalMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = Matrix<Rational>(
    countOfRows,
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

// endregion

// region Long aliases

@JvmName("LongRationalMatrix")
fun RationalMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    coefficients: List<List<Long>>
) = RationalMatrix(
    countOfRows,
    countOfColumns,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalMatrix")
fun RationalMatrix(
    coefficients: List<List<Long>>
) = RationalMatrix(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalMatrix")
fun RationalMatrix(
    vararg coefficients: List<Long>
) = RationalMatrix(
    coefficients.toList()
)

@JvmName("LongRationalMatrix")
fun RationalMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = Matrix<Rational>(
    countOfRows,
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

// endregion
// endregion

// region SquareMatrix

typealias RationalSquareMatrix = SquareMatrix<Rational>

// region Int aliases

@JvmName("IntRationalSquareMatrix")
fun RationalSquareMatrix(
    countOfRows: Int,
    coefficients: List<List<Int>>
) = RationalSquareMatrix(
    countOfRows,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalSquareMatrix")
fun RationalSquareMatrix(
    coefficients: List<List<Int>>
) = RationalSquareMatrix(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalSquareMatrix")
fun RationalSquareMatrix(
    vararg coefficients: List<Int>
) = RationalSquareMatrix(
    coefficients.toList()
)

@JvmName("IntRationalSquareMatrix")
fun RationalSquareMatrix(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = SquareMatrix<Rational>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

// endregion

// region Long aliases

@JvmName("LongRationalSquareMatrix")
fun RationalSquareMatrix(
    countOfRows: Int,
    coefficients: List<List<Long>>
) = RationalSquareMatrix(
    countOfRows,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalSquareMatrix")
fun RationalSquareMatrix(
    coefficients: List<List<Long>>
) = RationalSquareMatrix(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalSquareMatrix")
fun RationalSquareMatrix(
    vararg coefficients: List<Long>
) = RationalSquareMatrix(
    coefficients.toList()
)

@JvmName("LongRationalSquareMatrix")
fun RationalSquareMatrix(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = SquareMatrix<Rational>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

// endregion
// endregion

// region ColumnVector

typealias RationalColumnVector = ColumnVector<Rational>

// region Int aliases

@JvmName("IntRationalColumnVector")
fun RationalColumnVector(
    countOfRows: Int,
    coefficients: List<List<Int>>
) = RationalColumnVector(
    countOfRows,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalColumnVector")
fun RationalColumnVector(
    coefficients: List<List<Int>>
) = RationalColumnVector(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalColumnVector")
fun RationalColumnVector(
    vararg coefficients: Int
) = ColumnVector<Rational>(
    coefficients.map { listOf(it.toRational()) }
)

@JvmName("IntRationalColumnVector")
fun RationalColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = ColumnVector<Rational>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

@JvmName("IntRationalColumnVector")
fun RationalColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int) -> Int
) = ColumnVector<Rational>(
    countOfRows
) { rowIndex: Int -> init(rowIndex).toRational() }

// endregion

// region Long aliases

@JvmName("LongRationalColumnVector")
fun RationalColumnVector(
    countOfRows: Int,
    coefficients: List<List<Long>>
) = RationalColumnVector(
    countOfRows,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalColumnVector")
fun RationalColumnVector(
    coefficients: List<List<Long>>
) = RationalColumnVector(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalColumnVector")
fun RationalColumnVector(
    vararg coefficients: Long
) = ColumnVector<Rational>(
    coefficients.map { listOf(it.toRational()) }
)

@JvmName("LongRationalColumnVector")
fun RationalColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = ColumnVector<Rational>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

@JvmName("LongRationalColumnVector")
fun RationalColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int) -> Long
) = ColumnVector<Rational>(
    countOfRows
) { rowIndex: Int -> init(rowIndex).toRational() }

// endregion
// endregion

// region RowVector

typealias RationalRowVector = RowVector<Rational>

// region Int aliases

@JvmName("IntRationalRowVector")
fun RationalRowVector(
    countOfColumns: Int,
    coefficients: List<List<Int>>
) = RationalRowVector(
    countOfColumns,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalRowVector")
fun RationalRowVector(
    coefficients: List<List<Int>>
) = RationalRowVector(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("IntRationalRowVector")
fun RationalRowVector(
    vararg coefficients: Int
) = RowVector<Rational>(
    listOf(coefficients.map { it.toRational() })
)

@JvmName("IntRationalRowVector")
fun RationalRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = RowVector<Rational>(
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

@JvmName("IntRationalRowVector")
fun RationalRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int) -> Int
) = RowVector<Rational>(
    countOfColumns
) { rowIndex: Int -> init(rowIndex).toRational() }

// endregion

// region Long aliases

@JvmName("LongRationalRowVector")
fun RationalRowVector(
    countOfColumns: Int,
    coefficients: List<List<Long>>
) = RationalRowVector(
    countOfColumns,
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalRowVector")
fun RationalRowVector(
    coefficients: List<List<Long>>
) = RationalRowVector(
    coefficients.map { it.map { it.toRational() } }
)

@JvmName("LongRationalRowVector")
fun RationalRowVector(
    vararg coefficients: Long
) = RowVector<Rational>(
    listOf(coefficients.map { it.toRational() })
)

@JvmName("LongRationalRowVector")
fun RationalRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = RowVector<Rational>(
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toRational() }

@JvmName("LongRationalRowVector")
fun RationalRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int) -> Long
) = RowVector<Rational>(
    countOfColumns
) { rowIndex: Int -> init(rowIndex).toRational() }

// endregion
// endregion