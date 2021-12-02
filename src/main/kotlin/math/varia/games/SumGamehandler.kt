package math.varia.games

//class SumGamehandler(val subgames: Set<GameHandler<out Any>>): GameHandler<SumGamehandler.Turn<out Any>> {
//    constructor(vararg games: GameHandler<out Any>): this(games.toSet())
//
//    override fun getNimber() = subgames.fold(NimZero) { acc, subgame -> acc + subgame.getNimber() }
//
//    data class Turn<SubTurn : Any>(val subgame: GameHandler<SubTurn>, val subturn: SubTurn)
//
//    override fun isTurnCorrect(turn: Turn<*>): Boolean {
//        val (subgame, subturn) = turn
//        return subgame.isTurnCorrect(subturn)
//    }
//
//    override fun getTurnTo(to: Nimber): Turn<*> {
//        val currentNimber = getNimber()
//        if (to.value > currentNimber.value) throw IllegalArgumentException("Can't go to nimber $to being nimber $currentNimber")
//        val dif = to + currentNimber
//        for (subgame in subgames) {
//            val gamesNimber = subgame.getNimber()
//            if (gamesNimber >= dif + gamesNimber) {
//                val subturn = subgame.getTurnTo(gamesNimber + dif)
//                return Turn(subgame, subturn)
//            }
//        }
//        throw Error("Have not found in GameSum nimber to decrease and get $to from $currentNimber:\n${subgames.map(Game::getNimber)}")
//    }
//
//    override fun getWinningTurn(): ArrayList<Pair<Game, Any>>? = if (getNimber().value == 0) null else getTurnTo(Nimber(0))
//    override fun processTurn(turn: ArrayList<Pair<Game, Any>>) {
//        if (!isTurnCorrect(turn)) throw IllegalArgumentException("Incorrect turn")
//        turn[1].first.processTurn(ArrayList(turn.subList(1, turn.size)))
//    }
//}