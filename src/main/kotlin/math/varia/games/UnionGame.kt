package math.varia.games

class UnionGame(val games: List<ImpartialGame>): ImpartialGame {
    constructor(vararg games: ImpartialGame): this(games.toList())

    override fun getNimber(): Nimber =
        games
            .asSequence()
            .map { it.getNimber() }
            .mex()

    fun getGameHandler(): GameHandler<Any> {
        TODO("Not yet implemented")
    }
}