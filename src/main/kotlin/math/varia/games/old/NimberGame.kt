package math.varia.games.old

class NimberGame(var count: Int): Game {
    override lateinit var supergame: Game
    override val subgames: Set<Game> = emptySet()
    override val descendantGames: Set<Game> = emptySet()

    override fun initSupergame(game: Game) { supergame = game }

    override fun getNimber(): Nimber = Nimber(count)

    override fun isTurnCorrect(turn: ArrayList<Pair<Game, Any>>): Boolean =
        when {
            turn.size != 1 -> false
            turn[0].first != this -> false
            turn[0].second !in 1..count -> false
            else -> true
        }

    override fun getTurnTo(to: Nimber): ArrayList<Pair<Game, Any>> =
        if (to.value < count) arrayListOf(Pair(this, count - to.value)) else
            throw IllegalArgumentException("There is no turn to get to $to in ${getNimber()}-game")

    override fun getWinningTurn(): ArrayList<Pair<Game, Any>>? = arrayListOf(Pair(this, count))

    override fun processTurn(turn: ArrayList<Pair<Game, Any>>) {
        if (!isTurnCorrect(turn)) throw IllegalArgumentException("Cannot parse turn being ${getNimber()}-game:\n$turn")
        count -= turn[0].second as Int
    }
}
