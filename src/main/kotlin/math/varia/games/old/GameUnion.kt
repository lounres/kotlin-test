package math.varia.games.old

class GameUnion(vararg games: Game): Game {
    override lateinit var supergame: Game
    override val subgames: Set<Game> = setOf(*games)
    override val descendantGames: Set<Game> = games.fold(emptySet()) { set, T -> set union T.descendantGames}

    init {
        if (games.fold(0) {count, G -> count + G.descendantGames.size} != descendantGames.size)
            throw IllegalArgumentException("There is game that is used in at least two different subgames.")
    }

    var chosenSubgame: Game? = null
        private set(value) = if(field == null) {field = value} else throw IllegalAccessException("GameUnion.chosenGame is already initialised.")

    init {
        for (game in this.subgames) {
            game.initSupergame(this)
        }
    }

    override fun initSupergame(game: Game) { supergame = game }

    override fun getNimber() = if (chosenSubgame == null) mex(*subgames.map(Game::getNimber).toTypedArray()) else chosenSubgame!!.getNimber()

    override fun isTurnCorrect(turn: ArrayList<Pair<Game, Any>>): Boolean =
        if (chosenSubgame == null) {
            when {
                turn.size != 1 -> false
                turn[0].first != this -> false
                turn[0].second !is Game -> false
                turn[0].second !in subgames -> false
                else -> true
            }
        } else {
            when {
                turn.size < 2 -> false
                turn[0].first != this -> false
                turn[0].second !is Game -> false
                turn[0].second !in subgames -> false
                turn[1].first != turn[0].second -> false
                else -> turn[1].first.isTurnCorrect(ArrayList(turn.subList(1, turn.size)))
            }
        }

    override fun getTurnTo(to: Nimber): ArrayList<Pair<Game, Any>> {
        if (chosenSubgame == null) {
            val currentNimber = getNimber()
            if (to.value > currentNimber.value) throw IllegalArgumentException("Can't go to nimber $to being nimber $currentNimber")
            for (game in subgames) {
                if (game.getNimber() == to) {
                    return arrayListOf(this as Game to game as Any)
                }
            }
            throw Error("Have not found in GameSum nimber to decrease and get $to from $currentNimber:\n${subgames.map(
                Game::getNimber)}")
        } else {
            val res = arrayListOf(this as Game to chosenSubgame!! as Any)
            res.addAll(chosenSubgame!!.getTurnTo(to))
            return res
        }
    }

    override fun getWinningTurn(): ArrayList<Pair<Game, Any>>? = if (getNimber().value == 0) null else getTurnTo(Nimber(0))
    override fun processTurn(turn: ArrayList<Pair<Game, Any>>) {
        if (!isTurnCorrect(turn)) throw IllegalArgumentException("Incorrect turn")
        if (chosenSubgame == null) {
            chosenSubgame = turn[0].second as Game
        } else {
            turn[1].first.processTurn(ArrayList(turn.subList(1, turn.size)))
        }
    }
}