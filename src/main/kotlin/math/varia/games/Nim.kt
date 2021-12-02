package math.varia.games

data class Nim(val amounts: List<UInt>): ImpartialGame, HandleableGame {
    constructor(vararg amounts: UInt): this(amounts.toList())

    override fun getNimber(): Nimber =
        amounts
            .reduceOrNull { acc, amount -> acc xor amount }
            ?.toNimber()
            ?: NimZero

    override fun getHandler(): NimHandler = NimHandler(amounts)
}