package math.varia.games

operator fun UInt.compareTo(other: Nimber) = this.compareTo(other.value)
operator fun Int.compareTo(other: Nimber) = if (this < 0) -1 else this.toUInt().compareTo(other.value)

fun UInt.toNimber() = Nimber(this)
fun Int.toNimber() = Nimber(this.toUInt())

val NimZero = Nimber(0u)
val NimOne = Nimber(1u)