package math.linear

import math.ringsAndFields.*

// region Operator extensions

// region Field case

operator fun <T: Field<T>> Matrix<T>.div(other: T): Matrix<T> =
    if (other.isZero()) throw ArithmeticException("/ by zero")
    else
        Matrix(
            countOfRows,
            countOfColumns,
            coefficients
                .map {
                    it.map {
                        it / other
                    }
                },
            toCheckInput = false
        )

// endregion

// Constants

operator fun <T: Ring<T>> T.times(other: Matrix<T>): Matrix<T> =
    Matrix(
        other.countOfRows,
        other.countOfColumns,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Integer.times(other: Matrix<T>): Matrix<T> =
    Matrix(
        other.countOfRows,
        other.countOfColumns,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Int.times(other: Matrix<T>): Matrix<T> =
    Matrix(
        other.countOfRows,
        other.countOfColumns,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Long.times(other: Matrix<T>): Matrix<T> =
    Matrix(
        other.countOfRows,
        other.countOfColumns,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

// endregion

// endregion

// region Collections

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> Matrix<T>.map(transform: (T) -> S): Matrix<S> =
    Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> transform(coefficients[rowIndex][columnIndex]) }

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> Matrix<T>.mapIndexed(transform: (rowIndex: Int, columnIndex: Int, T) -> S): Matrix<S> =
    Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> transform(rowIndex, columnIndex, coefficients[rowIndex][columnIndex]) }

// endregion