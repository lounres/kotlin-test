package math.varia.games.old

class GameSum(vararg games: Game): Game {
    override lateinit var supergame: Game
    override val subgames: Set<Game> = setOf(*games)
    override val descendantGames: Set<Game> = games.fold(emptySet()) { set, G -> set union G.descendantGames}

    init {
        if (games.fold(0) {count, G -> count + G.descendantGames.size} != descendantGames.size)
            throw IllegalArgumentException("There is game that is used in at least two different subgames.")
    }

    init {
        for (game in this.subgames) {
            game.initSupergame(this)
        }
    }

    override fun initSupergame(game: Game) { supergame = game }

    override fun getNimber() = subgames.map(Game::getNimber).reduce(Nimber::plus)

    override fun isTurnCorrect(turn: ArrayList<Pair<Game, Any>>): Boolean =
        when {
            turn.size < 2 -> false
            turn[0].first != this -> false
            turn[0].second !is Game -> false
            turn[0].second !in subgames -> false
            turn[1].first != turn[0].second -> false
            else -> turn[1].first.isTurnCorrect(ArrayList(turn.subList(1, turn.size)))
        }

    override fun getTurnTo(to: Nimber): ArrayList<Pair<Game, Any>> {
        val currentNimber = getNimber()
        if (to.value > currentNimber.value) throw IllegalArgumentException("Can't go to nimber $to being nimber $currentNimber")
        val dif = to + currentNimber
        for (game in subgames) {
            val gamesNimber = game.getNimber()
            if (gamesNimber >= dif + gamesNimber) {
                val res = arrayListOf(this as Game to game as Any)
                res.addAll(game.getTurnTo(game.getNimber() + dif))
                return res
            }
        }
        throw Error("Have not found in GameSum nimber to decrease and get $to from $currentNimber:\n${subgames.map(Game::getNimber)}")
    }

    override fun getWinningTurn(): ArrayList<Pair<Game, Any>>? = if (getNimber().value == 0) null else getTurnTo(Nimber(0))
    override fun processTurn(turn: ArrayList<Pair<Game, Any>>) {
        if (!isTurnCorrect(turn)) throw IllegalArgumentException("Incorrect turn")
        turn[1].first.processTurn(ArrayList(turn.subList(1, turn.size)))
    }
}