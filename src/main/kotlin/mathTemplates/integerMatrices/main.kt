package mathTemplates.integerMatrices

import math.linear.*
import math.ringsAndFields.*

// region Matrix

typealias IntegerMatrix = Matrix<Integer>

// region Int aliases

@JvmName("IntIntegerMatrix")
fun IntegerMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    coefficients: List<List<Int>>
) = IntegerMatrix(
    countOfRows,
    countOfColumns,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerMatrix")
fun IntegerMatrix(
    coefficients: List<List<Int>>
) = IntegerMatrix(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerMatrix")
fun IntegerMatrix(
    vararg coefficients: List<Int>
) = IntegerMatrix(
    coefficients.toList()
)

@JvmName("IntIntegerMatrix")
fun IntegerMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = Matrix<Integer>(
    countOfRows,
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

// endregion

// region Long aliases

@JvmName("LongIntegerMatrix")
fun IntegerMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    coefficients: List<List<Long>>
) = IntegerMatrix(
    countOfRows,
    countOfColumns,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerMatrix")
fun IntegerMatrix(
    coefficients: List<List<Long>>
) = IntegerMatrix(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerMatrix")
fun IntegerMatrix(
    vararg coefficients: List<Long>
) = IntegerMatrix(
    coefficients.toList()
)

@JvmName("LongIntegerMatrix")
fun IntegerMatrix(
    countOfRows: Int,
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = Matrix<Integer>(
    countOfRows,
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

// endregion
// endregion

// region SquareMatrix

typealias IntegerSquareMatrix = SquareMatrix<Integer>

// region Int aliases

@JvmName("IntIntegerSquareMatrix")
fun IntegerSquareMatrix(
    countOfRows: Int,
    coefficients: List<List<Int>>
) = IntegerSquareMatrix(
    countOfRows,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerSquareMatrix")
fun IntegerSquareMatrix(
    coefficients: List<List<Int>>
) = IntegerSquareMatrix(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerSquareMatrix")
fun IntegerSquareMatrix(
    vararg coefficients: List<Int>
) = IntegerSquareMatrix(
    coefficients.toList()
)

@JvmName("IntIntegerSquareMatrix")
fun IntegerSquareMatrix(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = SquareMatrix<Integer>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

// endregion

// region Long aliases

@JvmName("LongIntegerSquareMatrix")
fun IntegerSquareMatrix(
    countOfRows: Int,
    coefficients: List<List<Long>>
) = IntegerSquareMatrix(
    countOfRows,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerSquareMatrix")
fun IntegerSquareMatrix(
    coefficients: List<List<Long>>
) = IntegerSquareMatrix(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerSquareMatrix")
fun IntegerSquareMatrix(
    vararg coefficients: List<Long>
) = IntegerSquareMatrix(
    coefficients.toList()
)

@JvmName("LongIntegerSquareMatrix")
fun IntegerSquareMatrix(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = SquareMatrix<Integer>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

// endregion
// endregion

// region ColumnVector

typealias IntegerColumnVector = ColumnVector<Integer>

// region Int aliases

@JvmName("IntIntegerColumnVector")
fun IntegerColumnVector(
    countOfRows: Int,
    coefficients: List<List<Int>>
) = IntegerColumnVector(
    countOfRows,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerColumnVector")
fun IntegerColumnVector(
    coefficients: List<List<Int>>
) = IntegerColumnVector(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerColumnVector")
fun IntegerColumnVector(
    vararg coefficients: Int
) = ColumnVector<Integer>(
    coefficients.map { listOf(it.toInteger()) }
)

@JvmName("IntIntegerColumnVector")
fun IntegerColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = ColumnVector<Integer>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

@JvmName("IntIntegerColumnVector")
fun IntegerColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int) -> Int
) = ColumnVector<Integer>(
    countOfRows
) { rowIndex: Int -> init(rowIndex).toInteger() }

// endregion

// region Long aliases

@JvmName("LongIntegerColumnVector")
fun IntegerColumnVector(
    countOfRows: Int,
    coefficients: List<List<Long>>
) = IntegerColumnVector(
    countOfRows,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerColumnVector")
fun IntegerColumnVector(
    coefficients: List<List<Long>>
) = IntegerColumnVector(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerColumnVector")
fun IntegerColumnVector(
    vararg coefficients: Long
) = ColumnVector<Integer>(
    coefficients.map { listOf(it.toInteger()) }
)

@JvmName("LongIntegerColumnVector")
fun IntegerColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = ColumnVector<Integer>(
    countOfRows
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

@JvmName("LongIntegerColumnVector")
fun IntegerColumnVector(
    countOfRows: Int,
    init: (rowIndex: Int) -> Long
) = ColumnVector<Integer>(
    countOfRows
) { rowIndex: Int -> init(rowIndex).toInteger() }

// endregion
// endregion

// region RowVector

typealias IntegerRowVector = RowVector<Integer>

// region Int aliases

@JvmName("IntIntegerRowVector")
fun IntegerRowVector(
    countOfColumns: Int,
    coefficients: List<List<Int>>
) = IntegerRowVector(
    countOfColumns,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerRowVector")
fun IntegerRowVector(
    coefficients: List<List<Int>>
) = IntegerRowVector(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("IntIntegerRowVector")
fun IntegerRowVector(
    vararg coefficients: Int
) = RowVector<Integer>(
    listOf(coefficients.map { it.toInteger() })
)

@JvmName("IntIntegerRowVector")
fun IntegerRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Int
) = RowVector<Integer>(
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

@JvmName("IntIntegerRowVector")
fun IntegerRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int) -> Int
) = RowVector<Integer>(
    countOfColumns
) { rowIndex: Int -> init(rowIndex).toInteger() }

// endregion

// region Long aliases

@JvmName("LongIntegerRowVector")
fun IntegerRowVector(
    countOfColumns: Int,
    coefficients: List<List<Long>>
) = IntegerRowVector(
    countOfColumns,
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerRowVector")
fun IntegerRowVector(
    coefficients: List<List<Long>>
) = IntegerRowVector(
    coefficients.map { it.map { it.toInteger() } }
)

@JvmName("LongIntegerRowVector")
fun IntegerRowVector(
    vararg coefficients: Long
) = RowVector<Integer>(
    listOf(coefficients.map { it.toInteger() })
)

@JvmName("LongIntegerRowVector")
fun IntegerRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int, columnIndex: Int) -> Long
) = RowVector<Integer>(
    countOfColumns
) { rowIndex: Int, columnIndex: Int -> init(rowIndex, columnIndex).toInteger() }

@JvmName("LongIntegerRowVector")
fun IntegerRowVector(
    countOfColumns: Int,
    init: (rowIndex: Int) -> Long
) = RowVector<Integer>(
    countOfColumns
) { rowIndex: Int -> init(rowIndex).toInteger() }

// endregion
// endregion