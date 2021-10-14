package math.varia.planimetricsCalculation

import math.ringsAndFields.*
import math.polynomials.*
import math.linear.*


// region Lying and tangency
fun lyingCondition(P: Point, l: Line) = P.x * l.x + P.y * l.y + P.z * l.z

fun lyingCondition(P: Point, q: Quadric) =
    P.x * P.x * q.xx +
            P.y * P.y * q.yy +
            P.z * P.z * q.zz +
            P.x * P.y * q.xy +
            P.x * P.z * q.xz +
            P.y * P.z * q.yz

fun tangencyCondition(l: Line, q: Quadric) =
    l.x * l.x * (q.yz * q.yz - q.yy * q.zz * 4) +
            l.y * l.y * (q.xz * q.xz - q.xx * q.zz * 4) +
            l.z * l.z * (q.xy * q.xy - q.xx * q.yy * 4) +
            l.x * l.y * (q.xy * q.zz * 2 - q.xz * q.yz) * 2 +
            l.x * l.z * (q.xz * q.yy * 2 - q.xy * q.yz) * 2 +
            l.y * l.z * (q.xx * q.yz * 2 - q.xy * q.xz) * 2

fun Point.isLyingOn(l: Line) = lyingCondition(this, l).isZero()

fun Point.isNotLyingOn(l: Line) = lyingCondition(this, l).isNotZero()

fun Point.isLyingOn(q: Quadric) = lyingCondition(this, q).isZero()

fun Point.isNotLyingOn(q: Quadric) = lyingCondition(this, q).isNotZero()

fun Line.isLyingThrough(P: Point) = lyingCondition(P, this).isZero()

fun Line.isNotLyingThrough(P: Point) = lyingCondition(P, this).isNotZero()

fun Line.isTangentTo(q: Quadric): Boolean = tangencyCondition(this, q).isZero()

fun Line.isNotTangentTo(q: Quadric): Boolean = tangencyCondition(this, q).isNotZero()

fun Quadric.isLyingThrough(P: Point) = lyingCondition(P, this).isZero()

fun Quadric.isNotLyingThrough(P: Point) = lyingCondition(P, this).isNotZero()

fun Quadric.isTangentTo(l: Line): Boolean = tangencyCondition(l, this).isZero()

fun Quadric.isNotTangentTo(l: Line): Boolean = tangencyCondition(l, this).isNotZero()
// endregion


// region Lines and segments
fun lineThrough(A: Point, B: Point) =
    Line(
        A.y * B.z - A.z * B.y,
        A.z * B.x - A.x * B.z,
        A.x * B.y - A.y * B.x
    )

fun intersectionOf(l: Line, m: Line) =
    Point(
        l.y * m.z - l.z * m.y,
        l.z * m.x - l.x * m.z,
        l.x * m.y - l.y * m.x
    )

fun collinearityCondition(A: Point, B: Point, C: Point) = A.x * (B.y * C.z - B.z * C.y) + B.x * (C.y * A.z - C.z * A.y) + C.x * (A.y * B.z - A.z * B.y)

fun concurrencyCondition(l: Line, m: Line, n: Line) = l.x * (m.y * n.z - m.z * n.y) + m.x * (n.y * l.z - n.z * l.y) + n.x * (l.y * m.z - l.z * m.y)


fun midpoint(A: Point, B: Point) =
    Point(
        A.x * B.z + B.x * A.z,
        A.y * B.z + B.y * A.z,
        2 * A.z * B.z
    )

fun divideSegmentInRatio(A: Point, B: Point, lambda: Rational) =
    Point(
        A.x * B.z + lambda * B.x * A.z,
        A.y * B.z + lambda * B.y * A.z,
        (lambda + 1) * A.z * B.z
    )

fun perpendicular(l: Line, A: Point) =
    Line(
        -l.y * A.z,
        l.x * A.z,
        A.x * l.y - A.y * l.x
    )

fun Point.projectTo(l: Line) = intersectionOf(l, perpendicular(l, this))
// endregion


// region Circles
fun Quadric.isCircle() = xy.isZero() && (xx - yy).isZero()

fun circleByCenterAndPoint(O: Point, A: Point) =
    Quadric(
        xx = O.z * O.z * A.z * A.z,
        yy = O.z * O.z * A.z * A.z,
        zz = (2 * O.x * A.z - O.z * A.x) * A.x * O.z + (2 * O.y * A.z - O.z * A.y) * A.y * O.z,
        xz = -2 * O.x * O.z * A.z * A.z,
        yz = -2 * O.y * O.z * A.z * A.z,
        xy = 0.toRational().toLabeledPolynomial()
    )

fun circleByDiameter(A: Point, B: Point) =
    Quadric(
        A.z * B.z,
        A.z * B.z,
        A.y * B.y + A.x * B.x,
        0.toRational().toLabeledPolynomial(),
        -(A.z * B.x + A.x * B.z),
        -(A.z * B.y + A.y * B.z)
    )

fun circumcenter(A: Point, B: Point, C: Point) =
    Point(
        (A.x * A.x + A.y * A.y) * (B.y * C.z - C.y * B.z) * B.z * C.z
                + (B.x * B.x + B.y * B.y) * (C.y * A.z - A.y * C.z) * C.z * A.z
                + (C.x * C.x + C.y * C.y) * (A.y * B.z - B.y * A.z) * A.z * B.z,
        (A.x * A.x + A.y * A.y) * (C.x * B.z - B.x * C.z) * B.z * C.z
                + (B.x * B.x + B.y * B.y) * (A.x * C.z - C.x * A.z) * C.z * A.z
                + (C.x * C.x + C.y * C.y) * (B.x * A.z - A.x * B.z) * A.z * B.z,
        2 * (A.x * (B.y * C.z - C.y * B.z) + B.x * (C.y * A.z - A.y * C.z) + C.x * (A.y * B.z - B.y * A.z)) * A.z * B.z * C.z
    )

fun circumcircle(A: Point, B: Point, C: Point) =
    Quadric(
        (A.x * B.y * C.z - A.x * B.z * C.y - A.y * B.x * C.z + A.y * B.z * C.x + A.z * B.x * C.y - A.z * B.y * C.x) * A.z * B.z * C.z,
        (A.x * B.y * C.z - A.x * B.z * C.y - A.y * B.x * C.z + A.y * B.z * C.x + A.z * B.x * C.y - A.z * B.y * C.x) * A.z * B.z * C.z,
        -((A.x * A.x + A.y * A.y) * B.x * B.z * C.y * C.z - (A.x * A.x + A.y * A.y) * B.y * B.z * C.x * C.z - A.x * A.z * (B.x * B.x + B.y * B.y) * C.y * C.z + A.x * A.z * B.y * B.z * (C.x * C.x + C.y * C.y) + A.y * A.z * (B.x * B.x + B.y * B.y) * C.x * C.z - A.y * A.z * B.x * B.z * (C.x * C.x + C.y * C.y)),
        0.toRational().toLabeledPolynomial(),
        -((A.x * A.x + A.y * A.y) * B.y * B.z * C.z * C.z - (A.x * A.x + A.y * A.y) * B.z * B.z * C.y * C.z - A.y * A.z * (B.x * B.x + B.y * B.y) * C.z * C.z + A.y * A.z * B.z * B.z * (C.x * C.x + C.y * C.y) + A.z * A.z * (B.x * B.x + B.y * B.y) * C.y * C.z - A.z * A.z * B.y * B.z * (C.x * C.x + C.y * C.y)),
        (A.x * A.x + A.y * A.y) * B.x * B.z * C.z * C.z - (A.x * A.x + A.y * A.y) * B.z * B.z * C.x * C.z - A.x * A.z * (B.x * B.x + B.y * B.y) * C.z * C.z + A.x * A.z * B.z * B.z * (C.x * C.x + C.y * C.y) + A.z * A.z * (B.x * B.x + B.y * B.y) * C.x * C.z - A.z * A.z * B.x * B.z * (C.x * C.x + C.y * C.y)
    )
// endregion


// region Points, lines and quadrics of triangle
fun centroid(A: Point, B: Point, C: Point) =
    Point(
        A.x * B.z * C.z + B.x * C.z * A.z + C.x * A.z * B.z,
        A.y * B.z * C.z + B.y * C.z * A.z + C.y * A.z * B.z,
        3 * A.z * B.z * C.z
    )

fun orthocenter(A: Point, B: Point, C: Point) =
    Point(
        A.y * B.z * C.z * (A.x * (C.x * B.z - B.x * C.z) + A.y * (C.y * B.z - B.y * C.z)) + B.y * C.z * A.z * (B.x * (A.x * C.z - C.x * A.z) + B.y * (A.y * C.z - C.y * A.z)) + C.y * A.z * B.z * (C.x * (B.x * A.z - A.x * B.z) + C.y * (B.y * A.z - A.y * B.z)),
        A.x * B.z * C.z * (A.y * (B.y * C.z - C.y * B.z) + A.x * (B.x * C.z - C.x * B.z)) + B.x * C.z * A.z * (B.y * (C.y * A.z - A.y * C.z) + B.x * (C.x * A.z - A.x * C.z)) + C.x * A.z * B.z * (C.y * (A.y * B.z - B.y * A.z) + C.x * (A.x * B.z - B.x * A.z)),
        A.z * B.z * C.z * (A.x * B.y * C.z - A.x * B.z * C.y - A.y * B.x * C.z + A.y * B.z * C.x + A.z * B.x * C.y - A.z * B.y * C.x)
    )

fun eulerLine(A: Point, B: Point, C: Point) =
    Line(
        ((A.y * (A.x * (B.y * C.z - C.y * B.z) - A.y * (B.x * C.z - C.x * B.z)) - 3 * A.x * (A.x * (B.x * C.z - C.x * B.z) + A.y * (B.y * C.z - C.y * B.z))) * B.z * C.z +
                (B.y * (B.x * (C.y * A.z - A.y * C.z) - B.y * (C.x * A.z - A.x * C.z)) - 3 * B.x * (B.x * (C.x * A.z - A.x * C.z) + B.y * (C.y * A.z - A.y * C.z))) * C.z * A.z +
                (C.y * (C.x * (A.y * B.z - B.y * A.z) - C.y * (A.x * B.z - B.x * A.z)) - 3 * C.x * (C.x * (A.x * B.z - B.x * A.z) + C.y * (A.y * B.z - B.y * A.z))) * A.z * B.z) * A.z * B.z * C.z,
        ((A.x * (A.x * (C.y * B.z - B.y * C.z) - A.y * (C.x * B.z - B.x * C.z)) + 3 * A.y * (A.x * (C.x * B.z - B.x * C.z) + A.y * (C.y * B.z - B.y * C.z))) * B.z * C.z +
                (B.x * (B.x * (A.y * C.z - C.y * A.z) - B.y * (A.x * C.z - C.x * A.z)) + 3 * B.y * (B.x * (A.x * C.z - C.x * A.z) + B.y * (A.y * C.z - C.y * A.z))) * C.z * A.z +
                (C.x * (C.x * (B.y * A.z - A.y * B.z) - C.y * (B.x * A.z - A.x * B.z)) + 3 * C.y * (C.x * (B.x * A.z - A.x * B.z) + C.y * (B.y * A.z - A.y * B.z))) * A.z * B.z) * A.z * B.z * C.z,
        (A.x * A.x + A.y * A.y) * (A.x * (B.x * C.z - C.x * B.z) + A.y * (B.y * C.z - C.y * B.z)) * B.z * B.z * C.z * C.z +
                (B.x * B.x + B.y * B.y) * (B.x * (C.x * A.z - A.x * C.z) + B.y * (C.y * A.z - A.y * C.z)) * C.z * C.z * A.z * A.z +
                (C.x * C.x + C.y * C.y) * (C.x * (A.x * B.z - B.x * A.z) + C.y * (A.y * B.z - B.y * A.z)) * A.z * A.z * B.z * B.z
    )

fun eulerCircle(A: Point, B: Point, C: Point) =
    Quadric(
        A.z * B.z * C.z * (A.x * (B.y * C.z - B.z * C.y)
                + A.y * (B.z * C.x - B.x * C.z)
                + A.z * (B.x * C.y - B.y * C.x)) * 2,
        A.z * B.z * C.z * (A.x * (B.y * C.z - B.z * C.y)
                + A.y * (B.z * C.x - B.x * C.z)
                + A.z * (B.x * C.y - B.y * C.x)) * 2,
        A.z * A.z * (B.x * C.x + B.y * C.y) * (B.x * C.y - B.y * C.x)
                + B.z * B.z * (C.x * A.x + C.y * A.y) * (C.x * A.y - C.y * A.x)
                + C.z * C.z * (A.x * B.x + A.y * B.y) * (A.x * B.y - A.y * B.x),
        0.toRational().toLabeledPolynomial(),
        A.z * A.z * ((B.x * C.z + C.x * B.z) * (B.y * C.x - C.y * B.x) + (B.y * C.z - C.y * B.z) * (B.x * C.x + B.y * C.y))
                + B.z * B.z * ((C.x * A.z + A.x * C.z) * (C.y * A.x - A.y * C.x) + (C.y * A.z - A.y * C.z) * (C.x * A.x + C.y * A.y))
                + C.z * C.z * ((A.x * B.z + B.x * A.z) * (A.y * B.x - B.y * A.x) + (A.y * B.z - B.y * A.z) * (A.x * B.x + A.y * B.y)),
        A.z * A.z * ((B.y * C.z + C.y * B.z) * (C.x * B.y - B.x * C.y) + (C.x * B.z - B.x * C.z) * (B.y * C.y + B.x * C.x))
                + B.z * B.z * ((C.y * A.z + A.y * C.z) * (A.x * C.y - C.x * A.y) + (A.x * C.z - C.x * A.z) * (C.y * A.y + C.x * A.x))
                + C.z * C.z * ((A.y * B.z + B.y * A.z) * (B.x * A.y - A.x * B.y) + (B.x * A.z - A.x * B.z) * (A.y * B.y + A.x * B.x))
    )
// endregion


// region Pole and polar
fun Point.polarBy(q: Quadric) = Line(rowVector * q.matrix)

fun Line.poleBy(q: Quadric) = Point(rowVector * q.matrix.adjugate())

fun Quadric.center() =
    Point(
        2 * xz * yy - xy * yz,
        2 * xx * yz - xy * xz,
        xy * xy - 4 * xx * yy
    )
// endregion

// region Quadrics
/**
 * See also: [wiki](https://en.wikipedia.org/wiki/Five_points_determine_a_conic#Construction)
 */
fun quadricByPoints(A: Point, B: Point, C: Point, D: Point, E: Point): Quadric =
    with(
        SquareMatrix(
            List(6) { 0.toRational().toLabeledPolynomial() },
            listOf(A.x * A.x, A.x * A.y, A.x * A.z, A.y * A.y, A.y * A.z, A.z * A.z),
            listOf(B.x * B.x, B.x * B.y, B.x * B.z, B.y * B.y, B.y * B.z, B.z * B.z),
            listOf(C.x * C.x, C.x * C.y, C.x * C.z, C.y * C.y, C.y * C.z, C.z * C.z),
            listOf(D.x * D.x, D.x * D.y, D.x * D.z, D.y * D.y, D.y * D.z, D.z * D.z),
            listOf(E.x * E.x, E.x * E.y, E.x * E.z, E.y * E.y, E.y * E.z, E.z * E.z)
        )
    ) {
        Quadric(
            xx =  minor(0, 0),
            xy = -minor(0, 1),
            xz =  minor(0, 2),
            yy = -minor(0, 3),
            yz =  minor(0, 4),
            zz = -minor(0, 5),
        )
    }

fun Line.projectToQuadricBy(q: Quadric, P: Point): Point =
    if (P.isNotLyingOn(this) || P.isNotLyingOn(q)) throw IllegalArgumentException("The point must lye on the line and the quadric.")
    else TODO("Not yet implemented")
//        Point(
//            q.xx * y * z * P.x + q.xy * y * z * P.y + q.xz * y * z * P.z - q.zz * x * y * P.z - q.yy * x * z * P.x,
//            (q.zz * x * x + q.xx * z * z - q.xz * x * z) * P.z,
//            (q.yy * x * x + q.xx * y * y - q.xy * x * y) * P.y
//        )

fun Point.projectToQuadricBy(q: Quadric, P: Point): Point =
    if (P.isNotLyingOn(q)) throw IllegalArgumentException("The point must lye on the quadric.")
    else TODO("Not yet implemented")
//        Point(
//            0.toRational().toLabeledPolynomial(),
//            0.toRational().toLabeledPolynomial(),
//            0.toRational().toLabeledPolynomial()
//        )

fun Point.projectToQuadricBy(q: Quadric, l: Line): Line =
    if (l.isNotTangentTo(q) || l.isNotLyingThrough(this)) throw IllegalArgumentException("The line must lye through the point and touch the quadric.")
    else TODO("Not yet implemented")
//        Point(
//            0.toRational().toLabeledPolynomial(),
//            0.toRational().toLabeledPolynomial(),
//            0.toRational().toLabeledPolynomial()
//        )

fun Line.projectToQuadricBy(q: Quadric, l: Line): Line =
    if (l.isNotTangentTo(q)) throw IllegalArgumentException("The line must touch the quadric.")
    else TODO("Not yet implemented")
//        Point(
//            0.toRational().toLabeledPolynomial(),
//            0.toRational().toLabeledPolynomial(),
//            0.toRational().toLabeledPolynomial()
//        )

// endregion

// region Transformations

fun involutionBy(A: Point, l: Line): Transformation =
    Transformation(
        ((l.rowVector * A.columnVector)[0, 0]).let {
            SquareMatrix(3) { rowIndex, columnIndex ->
                with(-2 * A.rowVector[rowIndex] * l.rowVector[columnIndex]) {
                    if (rowIndex == columnIndex) this + it else this
                }
            }
        }
    )

fun involutionBy(A: Point, q: Quadric): Transformation = involutionBy(A, A.polarBy(q))

fun involutionBy(l: Line, q: Quadric): Transformation = involutionBy(l.poleBy(q), l)

// endregion