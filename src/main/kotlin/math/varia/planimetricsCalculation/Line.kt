package math.varia.planimetricsCalculation

import math.ringsAndFields.*
import math.polynomials.*
import math.linear.*


data class Line (
    val x: LabeledPolynomial<Rational>,
    val y: LabeledPolynomial<Rational>,
    val z: LabeledPolynomial<Rational>
) {
    constructor(name: String) : this(
        Variable(name + "_x").toLabeledPolynomial(1.toRational()),
        Variable(name + "_y").toLabeledPolynomial(1.toRational()),
        Variable(name + "_z").toLabeledPolynomial(1.toRational())
    )

    constructor(rowVector: RowVector<LabeledPolynomial<Rational>>) : this(
        rowVector[0],
        rowVector[1],
        rowVector[2]
    )

    constructor(columnVector: ColumnVector<LabeledPolynomial<Rational>>) : this(
        columnVector[0],
        columnVector[1],
        columnVector[2]
    )

    val rowVector
        get() = RowVector(x, y, z)
    val columnVector
        get() = ColumnVector(x, y, z)

    override fun equals(other: Any?): Boolean =
        if (other !is Line) false
        else x * other.y == y * other.x && y * other.z == z * other.y && z * other.x == x * other.z

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }
}