package math.varia.games.old

/**
 * ## Game interface.
 *
 * It describes needed knowledge about any impartial game.
 *
 * @property supergame Game-parent. Is used in construction of the game tree. I recommend to make it always `lateinit`.
 * @property subgames Array list of all subgames. Is used in construction of the game tree.
 */
interface Game {
    val supergame: Game
    val subgames: Set<Game>
    val descendantGames: Set<Game>

    fun initSupergame(game: Game)

    /**
     * Every impartial game is equivalent to some nimber. So we should be able to compute it
     *
     * @return Associated with the game nimber.
     */
    fun getNimber(): Nimber

    /**
     * Checks if turn array is correct. Made to check turn before applying.
     */
    fun isTurnCorrect(turn: ArrayList<Pair<Game, Any>>): Boolean

    /**
     * Since the architecture is using recursive model
     * we need any block need to ask every subblock how to go to another nimber.
     *
     * @param to The nimber we want to get to.
     *
     * @return Array list of steps from top of the game tree to its bottom.
     */
    fun getTurnTo(to: Nimber): ArrayList<Pair<Game, Any>>

    /**
     * Also we sometimes want to get winning strategy.
     *
     * @return Array list of steps from top of the game tree to its bottom.
     */
    fun getWinningTurn(): ArrayList<Pair<Game, Any>>?

    /**
     * And we need to process a turn to get new game state and keep playing.
     *
     * @param turn Array list of steps from top of the game tree to its bottom.
     */
    fun processTurn(turn: ArrayList<Pair<Game, Any>>)
}