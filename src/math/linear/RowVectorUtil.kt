package math.linear

import math.ringsAndFields.*

// region Operator extensions

// region Field case

operator fun <T: Field<T>> RowVector<T>.div(other: T): RowVector<T> =
    if (other.isZero()) throw ArithmeticException("/ by zero")
    else
        RowVector(
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

operator fun <T: Ring<T>> T.times(other: RowVector<T>): RowVector<T> =
    RowVector(
        other.countOfColumns,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Integer.times(other: RowVector<T>): RowVector<T> =
    RowVector(
        other.countOfColumns,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Int.times(other: RowVector<T>): RowVector<T> =
    RowVector(
        other.countOfColumns,
        other.coefficients
            .map {
                it.map {
                    this * it
                }
            },
        toCheckInput = false
    )

operator fun <T: Ring<T>> Long.times(other: RowVector<T>): RowVector<T> =
    RowVector(
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
fun <T: Ring<T>, S: Ring<S>> RowVector<T>.map(transform: (T) -> S): RowVector<S> =
    RowVector(countOfColumns) { rowIndex, columnIndex -> transform(coefficients[rowIndex][columnIndex]) }

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> RowVector<T>.mapIndexed(transform: (columnIndex: Int, T) -> S): RowVector<S> =
    RowVector(countOfColumns) { columnIndex -> transform(columnIndex, coefficients[0][columnIndex]) }

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> RowVector<T>.mapIndexed(transform: (rowIndex: Int, columnIndex: Int, T) -> S): RowVector<S> =
    RowVector(countOfColumns) { rowIndex, columnIndex -> transform(rowIndex, columnIndex, coefficients[rowIndex][columnIndex]) }

// endregion