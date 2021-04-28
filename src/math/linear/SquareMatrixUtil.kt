package math.linear

import math.ringsAndFields.*


// region Operator extensions

// region Field case

operator fun <T: Field<T>> SquareMatrix<T>.div(other: T): SquareMatrix<T> =
    if (other.isZero()) throw ArithmeticException("/ by zero")
    else
        SquareMatrix(countOfRows) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] / other }

fun <T: Field<T>> SquareMatrix<T>.reciprocalOrNull(): SquareMatrix<T>? {
    val coefficients2 = coefficients.map { it.toMutableList() }.toMutableList()
    val ZERO = ringZero
    val ONE = ringOne
    val resultCoefficients = MutableList(countOfRows) { rowIndex -> MutableList(countOfRows) { columnIndex -> if (rowIndex == columnIndex) ONE else ZERO} }

    for  (columnNow in 0 until countOfColumns) {
        var coolRow = coefficients2.asSequence().drop(columnNow).indexOfFirst { it[columnNow].isNotZero() }
        if (coolRow == -1) return null

        coolRow += columnNow

        if (coolRow != columnNow) {
        coefficients2[coolRow] = coefficients2[columnNow].also { coefficients2[columnNow] = coefficients2[coolRow] }
            resultCoefficients[coolRow] = resultCoefficients[columnNow].also { resultCoefficients[columnNow] = resultCoefficients[coolRow] }
        }

        val coolCoef = coefficients2[columnNow][columnNow]

        for (col in 0 .. coefficients2.lastIndex) {
            coefficients2[columnNow][col] /= coolCoef
            resultCoefficients[columnNow][col] /= coolCoef
        }

        for (row in 0 .. coefficients2.lastIndex) {
            if (row == columnNow) continue
            val rowCoef = coefficients2[row][columnNow]
            for (col in 0 .. coefficients2.lastIndex) {
                coefficients2[row][col] = coefficients2[row][col] - coefficients2[columnNow][col] * rowCoef
                resultCoefficients[row][col] = resultCoefficients[row][col] - resultCoefficients[columnNow][col] * rowCoef
            }
        }
    }

    return SquareMatrix(
        countOfRows,
        resultCoefficients,
        toCheckInput = false
    )
}

fun <T: Field<T>> SquareMatrix<T>.reciprocal(): SquareMatrix<T> =
    reciprocalOrNull() ?: throw IllegalArgumentException("The square matrix has no reciprocal")

// endregion

// Constants

operator fun <T: Ring<T>> T.times(other: SquareMatrix<T>): SquareMatrix<T> =
    SquareMatrix(other.countOfRows) { rowIndex, columnIndex -> this * other.coefficients[rowIndex][columnIndex] }

operator fun <T: Ring<T>> Integer.times(other: SquareMatrix<T>): SquareMatrix<T> =
    SquareMatrix(other.countOfRows) { rowIndex, columnIndex -> this * other.coefficients[rowIndex][columnIndex] }

operator fun <T: Ring<T>> Int.times(other: SquareMatrix<T>): SquareMatrix<T> =
    SquareMatrix(other.countOfRows) { rowIndex, columnIndex -> this * other.coefficients[rowIndex][columnIndex] }

operator fun <T: Ring<T>> Long.times(other: SquareMatrix<T>): SquareMatrix<T> =
    SquareMatrix(other.countOfRows) { rowIndex, columnIndex -> this * other.coefficients[rowIndex][columnIndex] }

// endregion

// endregion

// region Collections

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> SquareMatrix<T>.map(transform: (T) -> S): SquareMatrix<S> =
    SquareMatrix(countOfRows) { rowIndex, columnIndex -> transform(coefficients[rowIndex][columnIndex]) }

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> SquareMatrix<T>.mapIndexed(transform: (rowIndex: Int, columnIndex: Int, T) -> S): SquareMatrix<S> =
    SquareMatrix(countOfRows) { rowIndex, columnIndex -> transform(rowIndex, columnIndex, coefficients[rowIndex][columnIndex]) }

// endregion