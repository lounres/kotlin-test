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

///**
// * Returns an element at the given [index] or the result of calling the [defaultValue] function if the [index] is out of bounds of this list.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.getOrElse(rowIndex: Int, columnIndex: Int, defaultValue: (Int, Int) -> T): T =
    when {
        rowIndex !in 0 until countOfRows -> defaultValue(rowIndex, columnIndex)
        columnIndex !in 0 until countOfColumns -> defaultValue(rowIndex, columnIndex)
        else -> coefficients[rowIndex][columnIndex]
    }

///**
// * Returns an element at the given [index] or the result of calling the [defaultValue] function if the [index] is out of bounds of this list.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.getOrElse(index: Pair<Int, Int>, defaultValue: (Pair<Int, Int>) -> T): T =
    when {
        index.first !in 0 until countOfRows -> defaultValue(index)
        index.second !in 0 until countOfColumns -> defaultValue(index)
        else -> coefficients[index.first][index.second]
    }

///**
// * Returns an element at the given [index] or the result of calling the [defaultValue] function if the [index] is out of bounds of this list.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.getOrElse(index: MatrixIndex, defaultValue: (MatrixIndex) -> T): T =
    when {
        index.rowIndex !in 0 until countOfRows -> defaultValue(index)
        index.columnIndex !in 0 until countOfColumns -> defaultValue(index)
        else -> coefficients[index.rowIndex][index.columnIndex]
    }

///**
// * Returns an element at the given [index] or `null` if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.getOrNull(rowIndex: Int, columnIndex: Int): T? =
    when {
        rowIndex !in 0 until countOfRows -> null
        columnIndex !in 0 until countOfColumns -> null
        else -> coefficients[rowIndex][columnIndex]
    }

///**
// * Returns an element at the given [index] or `null` if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.getOrNull(index: Pair<Int, Int>): T? =
    when {
        index.first !in 0 until countOfRows -> null
        index.second !in 0 until countOfColumns -> null
        else -> coefficients[index.first][index.second]
    }

///**
// * Returns an element at the given [index] or `null` if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.getOrNull(index: MatrixIndex): T? =
    when {
        index.rowIndex !in 0 until countOfRows -> null
        index.columnIndex !in 0 until countOfColumns -> null
        else -> coefficients[index.rowIndex][index.columnIndex]
    }

///**
// * Returns an element at the given [index] or throws an [IndexOutOfBoundsException] if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.elementMatrixAt(rowIndex: Int, columnIndex: Int): T = get(rowIndex, columnIndex)

///**
// * Returns an element at the given [index] or throws an [IndexOutOfBoundsException] if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.elementMatrixAt(index: Pair<Int, Int>): T = get(index)

///**
// * Returns an element at the given [index] or throws an [IndexOutOfBoundsException] if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.elementMatrixAt(index: MatrixIndex): T = get(index)

///**
// * Returns an element at the given [index] or the result of calling the [defaultValue] function if the [index] is out of bounds of this list.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.elementMatrixAtOrElse(rowIndex: Int, columnIndex: Int, defaultValue: (Int, Int) -> T): T =
    getOrElse(rowIndex, columnIndex, defaultValue)

///**
// * Returns an element at the given [index] or the result of calling the [defaultValue] function if the [index] is out of bounds of this list.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.elementMatrixAtOrElse(index: Pair<Int, Int>, defaultValue: (Pair<Int, Int>) -> T): T =
    getOrElse(index, defaultValue)

///**
// * Returns an element at the given [index] or the result of calling the [defaultValue] function if the [index] is out of bounds of this list.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.elementMatrixAtOrElse(index: MatrixIndex, defaultValue: (MatrixIndex) -> T): T =
    getOrElse(index, defaultValue)

///**
// * Returns an element at the given [index] or `null` if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.elementMatrixAtOrNull(rowIndex: Int, columnIndex: Int): T? = getOrNull(rowIndex, columnIndex)

///**
// * Returns an element at the given [index] or `null` if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.elementMatrixAtOrNull(index: Pair<Int, Int>): T? = getOrNull(index)

///**
// * Returns an element at the given [index] or `null` if the [index] is out of bounds of this list.
// */
fun <T: Ring<T>> Matrix<T>.elementMatrixAtOrNull(index: MatrixIndex): T? = getOrNull(index)

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> Matrix<T>.mapMatrix(transform: (T) -> S): Matrix<S> =
    Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> transform(coefficients[rowIndex][columnIndex]) }

/**
 * Returns a matrix containing the results of applying the given [transform] function
 * to each element in the original matrix.
 */
fun <T: Ring<T>, S: Ring<S>> Matrix<T>.mapMatrixIndexed(transform: (rowIndex: Int, columnIndex: Int, T) -> S): Matrix<S> =
    Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> transform(rowIndex, columnIndex, coefficients[rowIndex][columnIndex]) }

///**
// * Returns a lazy [Iterable] that wraps each element of the original collection
// * into an [IndexedValue] containing the index of that element and the element itself.
// */
fun <T: Ring<T>> Matrix<T>.withMatrixIndex(): Iterable<IndexedMatrixValue<T>> =
    object : Iterable<IndexedMatrixValue<T>> {
        override fun iterator(): Iterator<IndexedMatrixValue<T>> =
            object : Iterator<IndexedMatrixValue<T>> {
                var cursorRow = 0 // row index of next element to return
                var cursorColumn = 0 // column index of next element to return

                override fun hasNext(): Boolean = cursorRow != countOfRows

                override fun next(): IndexedMatrixValue<T> {
                    if (cursorRow == countOfRows) throw NoSuchElementException()
                    return IndexedMatrixValue(
                        MatrixIndex(cursorRow, cursorColumn),
                        coefficients[cursorRow][cursorColumn].also {
                            if (cursorColumn == countOfColumns - 1) {
                                cursorColumn = 0
                                cursorRow += 1
                            } else {
                                cursorColumn += 1
                            }
                        }
                    )
                }
            }
    }

///**
// * Returns `true` if all elements match the given [predicate].
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.allMatrix(predicate: (T) -> Boolean): Boolean {
    for (element in this) if (!predicate(element)) return false
    return true
}

///**
// * Returns `true` if all elements match the given [predicate].
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.allMatrixIndexed(predicate: (rowIndex: Int, columnIndex: Int, T) -> Boolean): Boolean {
    for ((index, element) in this.withMatrixIndex()) if (!predicate(index.rowIndex, index.columnIndex, element)) return false
    return true
}

///**
// * Returns `true` if at least one element matches the given [predicate].
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.anyMatrix(predicate: (T) -> Boolean): Boolean {
    for (element in this) if (predicate(element)) return true
    return false
}

///**
// * Returns `true` if at least one element matches the given [predicate].
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.anyMatrixIndexed(predicate: (rowIndex: Int, columnIndex: Int, T) -> Boolean): Boolean {
    for ((index, element) in this.withMatrixIndex()) if (predicate(index.rowIndex, index.columnIndex, element)) return true
    return false
}

///**
// * Accumulates value starting with [initial] value and applying [operation] from left to right
// * to current accumulator value and each element.
// *
// * Returns the specified [initial] value if the collection is empty.
// *
// * @param [operation] function that takes current accumulator value and an element, and calculates the next accumulator value.
// */
/*inline*/ fun <T: Ring<T>, R> Matrix<T>.foldMatrix(initial: R, operation: (acc: R, T) -> R): R {
    var accumulator = initial
    for (element in this) accumulator = operation(accumulator, element)
    return accumulator
}

///**
// * Accumulates value starting with [initial] value and applying [operation] from left to right
// * to current accumulator value and each element with its index in the original collection.
// *
// * Returns the specified [initial] value if the collection is empty.
// *
// * @param [operation] function that takes the index of an element, current accumulator value
// * and the element itself, and calculates the next accumulator value.
// */
/*inline*/ fun <T: Ring<T>, R> Matrix<T>.foldMatrixIndexed(initial: R, operation: (rowIndex: Int, columnIndex: Int, acc: R, T) -> R): R {
    var accumulator = initial
    for ((index, element) in this.withMatrixIndex()) accumulator = operation(index.rowIndex, index.columnIndex, accumulator, element)
    return accumulator
}

///**
// * Performs the given [action] on each element.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.forMatrixEach(action: (T) -> Unit) {
    for (element in this) action(element)
}

///**
// * Performs the given [action] on each element, providing sequential index with the element.
// * @param [action] function that takes the index of an element and the element itself
// * and performs the action on the element.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.forMatrixEachIndexed(action: (rowIndex: Int, columnIndex: Int, T) -> Unit) {
    for ((index, item) in this.withMatrixIndex()) action(index.rowIndex, index.columnIndex, item)
}

///**
// * Performs the given [action] on each element and returns the collection itself afterwards.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.onMatrixEach(action: (T) -> Unit): Matrix<T> = apply { forMatrixEach(action) }

///**
// * Performs the given [action] on each element, providing sequential index with the element,
// * and returns the collection itself afterwards.
// * @param [action] function that takes the index of an element and the element itself
// * and performs the action on the element.
// */
/*inline*/ fun <T: Ring<T>> Matrix<T>.onMatrixEachIndexed(action: (rowIndex: Int, columnIndex: Int, T) -> Unit): Matrix<T> = apply { forMatrixEachIndexed(action) }

// endregion