package math

infix fun <T, S, R> ((S) -> R).comp(other: (T) -> S) = { arg: T -> this(other(arg)) }

//operator fun <T, S, R> ((S) -> R).times(other: (T) -> S) = { arg: T -> this(other(arg)) }

infix fun <T> ((T) -> T).repeat(times: Int): (T) -> T =
    when {
        times < 0 -> throw IllegalArgumentException("Can't use function negative number of times")
        times == 0 -> { arg: T -> arg }
        times % 2 == 0 -> ({ arg: T -> this(this(arg)) } repeat times / 2)
        else -> ({ arg: T -> this(this(arg)) } repeat times / 2) comp this
    }