package math.linear

import math.ringsAndFields.*

// region Operator extensions

// region Field case

operator fun <T: Field<T>> ColumnVector<T>.div(other: T): ColumnVector<T> =
    if (other.isZero()) throw ArithmeticException("/ by zero")
    else
        ColumnVector(
            countOfRows,
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

operator fun <T: Ring<T>> T.times(other: ColumnVector<T>): ColumnVector<T> =
    ColumnVector(
        other.countOfRows,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Integer.times(other: ColumnVector<T>): ColumnVector<T> =
    ColumnVector(
        other.countOfRows,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Int.times(other: ColumnVector<T>): ColumnVector<T> =
    ColumnVector(
        other.countOfRows,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Long.times(other: ColumnVector<T>): ColumnVector<T> =
    ColumnVector(
        other.countOfRows,
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
fun <T: Ring<T>, S: Ring<S>> ColumnVector<T>.map(transform: (T) -> S): ColumnVector<S> =
    ColumnVector(countOfColumns) { rowIndex, columnIndex -> transform(coefficients[rowIndex][columnIndex]) }

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> ColumnVector<T>.mapIndexed(transform: (rowIndex: Int, T) -> S): ColumnVector<S> =
    ColumnVector(countOfColumns) { rowIndex -> transform(rowIndex, coefficients[rowIndex][0]) }

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> ColumnVector<T>.mapIndexed(transform: (rowIndex: Int, columnIndex: Int, T) -> S): ColumnVector<S> =
    ColumnVector(countOfColumns) { rowIndex, columnIndex -> transform(rowIndex, columnIndex, coefficients[rowIndex][columnIndex]) }

// endregion