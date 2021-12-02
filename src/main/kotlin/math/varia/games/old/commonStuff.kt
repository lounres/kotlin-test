package math.varia.games.old

fun mex(vararg nums: Int): Int {
    var res = 0
    while (res in nums) res++
    return res
}

fun mex(vararg nims: Nimber): Nimber {
    return Nimber(mex(*(nims.map(Nimber::value)).toIntArray()))
}