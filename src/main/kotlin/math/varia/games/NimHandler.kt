package math.varia.games


class NimHandler (amounts: List<UInt>): GameHandler<NimHandler.Turn> {
    private val _amounts: MutableList<UInt> = amounts.toMutableList()
    val amounts
        get() =  _amounts
    constructor(vararg amounts: UInt): this(amounts.toList())

    override fun getNimber(): Nimber =
        amounts
            .reduceOrNull { acc, amount -> acc xor amount }
            ?.toNimber()
            ?: NimZero

    data class Turn(val index:Int, val amount: UInt)

    override fun isTurnCorrect(turn: Turn): Boolean = with(amounts) {
        val (index, amount) = turn
        index in indices && amount <= this[index]
    }

    override fun getTurnTo(to: Nimber): Turn {
        val currentNimber = getNimber().value
        if (to.value > currentNimber) throw IllegalArgumentException("Can't go to nimber $to being nimber *$currentNimber")
        val dif = to.value xor currentNimber
        amounts.forEachIndexed { index, amount -> if (amount >= dif xor amount) return Turn(index, amount - dif xor amount) }
        throw Error("Have not found in NimHadler nimber to decrease and get $to from $currentNimber. Current amounts are: $amounts")
    }

    override fun processTurn(turn: Turn) {
        val (index, amount) = turn
        if (!(index in _amounts.indices && amount <= _amounts[index])) throw IllegalArgumentException("Incorrect turn")
        _amounts[index] -= amount
    }
}