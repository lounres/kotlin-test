package math.varia.games

class SumGame(val games: Set<ImpartialGame>): ImpartialGame {
    constructor(vararg games: ImpartialGame): this(games.toSet())

    override fun getNimber(): Nimber =
        games
            .asSequence()
            .map { it.getNimber().value }
            .reduceOrNull { acc, nimber -> acc xor nimber }
            ?.toNimber()
            ?: NimZero

    fun getHandler(): GameHandler<Any> {
        TODO("Not yet implemented")
    }
}