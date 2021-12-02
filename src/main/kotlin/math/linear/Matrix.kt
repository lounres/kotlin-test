package math.linear

import math.ringsAndFields.*


open class Matrix<T: Ring<T>> internal constructor(
    val countOfRows: Int,
    val countOfColumns: Int,
    val coefficients: List<List<T>>,
    toCheckInput: Boolean = true
) : Iterable<T> {
    init {
        if (toCheckInput) {
            if (countOfRows <= 0) throw IllegalArgumentException("Count of rows must be positive")
            if (countOfColumns <= 0) throw IllegalArgumentException("Count of columns must be positive")

            if (coefficients.size != countOfRows) throw IllegalArgumentException("Incorrect count of rows: $countOfRows expected, ${coefficients.size} got")
            coefficients
                .forEachIndexed { index, row ->
                    if (row.size != countOfColumns) throw IllegalArgumentException("Incorrect count of columns: $countOfColumns expected, ${row.size} got in row $index")
                }
        }
    }

    open val rank: Int by lazy {
        val coefficients2 = coefficients.map { it.toMutableList() }.toMutableList()

        var columnNow = 0
        var fromRow = 0
        while (columnNow < countOfColumns && fromRow < countOfRows) {
            var coolRow = coefficients2.asSequence().drop(fromRow).indexOfFirst { it[columnNow].isNotZero() }
            if (coolRow == -1) {
                columnNow++
                continue
            }
            else coolRow += fromRow

            if (coolRow != fromRow) {
                coefficients2[coolRow] = coefficients2[fromRow].also { coefficients2[fromRow] = coefficients2[coolRow] }
            }

            val coolCoef = coefficients2[fromRow][columnNow]

            for (row in fromRow + 1 .. coefficients2.lastIndex) {
                val rowCoef = coefficients2[row][columnNow]
                for (col in columnNow + 1 .. coefficients2.lastIndex)
                    coefficients2[row][col] = coefficients2[row][col] * coolCoef - coefficients2[fromRow][col] * rowCoef
            }

            columnNow++
            fromRow++
        }

        fromRow
    }

    val size: Int get() = countOfRows * countOfColumns
    val sizes: Pair<Int, Int> get() = Pair(countOfRows, countOfColumns)
    val rowIndices : Iterable<Int> get() = 0 until countOfRows
    val columnIndices : Iterable<Int> get() = 0 until countOfColumns
    val indices: List<MatrixIndex>
        get() = rowIndices.flatMap { rowIndex -> columnIndices.map { columnIndex -> MatrixIndex(rowIndex, columnIndex) } }

    val horizontalCoefficientsList : List<T>
        get() = coefficients.flatten()
    val verticalCoefficientsList : List<T>
        get() = mutableListOf<T>()
            .apply {
                for (columnIndex in columnIndices) for (rowIndex in rowIndices) {
                    add(coefficients[rowIndex][columnIndex])
                }
            }

    internal val ringExemplar: T get() = coefficients.first().first()
    internal val ringOne: T get() = ringExemplar.getOne()
    internal val ringZero: T get() = ringExemplar.getZero()

    internal constructor(coefficients: List<List<T>>, toCheckInput: Boolean = true) : this(
        coefficients.size.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        coefficients.first().size.also { if (it == 0) throw IllegalArgumentException("Count of columns must be positive") },
        coefficients,
        toCheckInput = toCheckInput
    )
    internal constructor(vararg coefficients: List<T>, toCheckInput: Boolean) : this(coefficients.toList(), toCheckInput)
    constructor(countOfRows: Int, countOfColumns: Int, coefficients: List<List<T>>) : this(
        countOfRows,
        countOfColumns,
        coefficients,
        toCheckInput = true
    )
    constructor(coefficients: List<List<T>>) : this(
        coefficients.size.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        coefficients.first().size.also { if (it == 0) throw IllegalArgumentException("Count of columns must be positive") },
        coefficients,
        toCheckInput = true
    )
    constructor(vararg coefficients: List<T>) : this(coefficients.toList())
    constructor(countOfRows: Int, countOfColumns: Int, init: (rowIndex: Int, columnIndex: Int) -> T) : this(
        countOfRows.also { if (it == 0) throw IllegalArgumentException("Count of rows must be positive") },
        countOfColumns.also { if (it == 0) throw IllegalArgumentException("Count of columns must be positive") },
        List(countOfRows) { rowIndex -> List(countOfColumns) { columnIndex -> init(rowIndex, columnIndex) } },
        toCheckInput = false
    )

    open operator fun unaryPlus(): Matrix<T> = this

    open operator fun unaryMinus(): Matrix<T> =
        Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> -coefficients[rowIndex][columnIndex] }

    operator fun plus(other: Matrix<T>): Matrix<T> =
        if (countOfRows != other.countOfRows || countOfColumns != other.countOfColumns) throw IllegalArgumentException("Can not add two matrices of different sizes")
        else
            Matrix(
                countOfRows,
                countOfColumns,
                coefficients
                    .mapIndexed { rowIndex, row ->
                        val row2 = other.coefficients[rowIndex]
                        row
                            .mapIndexed { columnIndex, t -> t + row2[columnIndex] }
                    },
                toCheckInput = false
            )

    operator fun minus(other: Matrix<T>): Matrix<T> =
        if (countOfRows != other.countOfRows || countOfColumns != other.countOfColumns) throw IllegalArgumentException("Can not subtract two matrices of different sizes")
        else
            Matrix(
                countOfRows,
                countOfColumns,
                coefficients
                    .mapIndexed { rowIndex, row ->
                        val row2 = other.coefficients[rowIndex]
                        row
                            .mapIndexed { columnIndex, t -> t - row2[columnIndex] }
                    },
                toCheckInput = false
            )

    operator fun times(other: Matrix<T>): Matrix<T> =
        if (countOfColumns != other.countOfRows) throw IllegalArgumentException("Can not multiply two matrices with not matching sizes")
        else
            Matrix(
                countOfRows,
                other.countOfColumns,
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

    open operator fun times(other: T): Matrix<T> =
        Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    open operator fun times(other: Integer): Matrix<T> =
        Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    open operator fun times(other: Int): Matrix<T> =
        Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    open operator fun times(other: Long): Matrix<T> =
        Matrix(countOfRows, countOfColumns) { rowIndex, columnIndex -> coefficients[rowIndex][columnIndex] * other }

    operator fun get(rowIndex: Int, columnIndex: Int) : T =
        when {
            rowIndex !in 0 until countOfRows -> throw IndexOutOfBoundsException("Row index out of bounds: $rowIndex got, in 0..${countOfRows-1} expected")
            columnIndex !in 0 until countOfColumns -> throw IndexOutOfBoundsException("Column index out of bounds: $columnIndex got, in 0..${countOfColumns-1} expected")
            else -> coefficients[rowIndex][columnIndex]
        }

    operator fun get(index: Pair<Int, Int>) : T = get(index.first, index.second)

    operator fun get(index: MatrixIndex) : T = get(index.rowIndex, index.columnIndex)

    override fun iterator(): Iterator<T> =
        object : Iterator<T> {
            var cursorRow = 0 // row index of next element to return
            var cursorColumn = 0 // column index of next element to return

            override fun hasNext(): Boolean = cursorRow != countOfRows

            override fun next(): T {
                if (cursorRow == countOfRows) throw NoSuchElementException()
                return coefficients[cursorRow][cursorColumn].also {
                    if (cursorColumn == countOfColumns - 1) {
                        cursorColumn = 0
                        cursorRow += 1
                    } else {
                        cursorColumn += 1
                    }
                }
            }
        }

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is Matrix<*> -> false
            countOfRows != other.countOfRows -> false
            countOfColumns != other.countOfColumns -> false
            else -> {
                val ringType = ringExemplar::class.java

                if (other.coefficients.all { it.all { ringType.isInstance(it) } }) {
                    @Suppress("UNCHECKED_CAST")
                    other as Matrix<T>

                    coefficients == other.coefficients
                } else false
            }
        }

    override fun hashCode(): Int {
        return coefficients.hashCode()
    }

    override fun toString(): String =
        coefficients.joinToString(prefix = "{", postfix = "}") { it.joinToString(prefix = "{", postfix = "}") { it.toString() } }

    fun subMatrix(rows: Iterable<Int>, columns: Iterable<Int>): Matrix<T> {
        val cleanedRows = rows.asSequence().apply { when { // TODO: Check WTH is this.
            first() < 0 -> throw IllegalArgumentException("Can't select row with index less than 0")
            last() >= countOfRows -> throw IllegalArgumentException("Can't select row with index more than max row index")
        } }.distinct().sorted().toList().apply { if(isEmpty()) throw IllegalArgumentException("No row is selected") }
        val cleanedColumns = columns.asSequence().apply { when {
            first() < 0 -> throw IllegalArgumentException("Can't select column with index less than 0")
            last() >= countOfRows -> throw IllegalArgumentException("Can't select column with index more than max column index")
        } }.distinct().sorted().toList().apply { if(isEmpty()) throw IllegalArgumentException("No column is selected") }
        return Matrix(
            coefficients.slice(cleanedRows)
                .map { it.slice(cleanedColumns) },
            toCheckInput = false
        )
    }

    open fun transposed() =
        Matrix(countOfColumns, countOfRows) { rowIndex, columnIndex -> coefficients[columnIndex][rowIndex] }
}