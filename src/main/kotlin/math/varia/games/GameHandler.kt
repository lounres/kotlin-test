package math.varia.games

/**
 * Allows to emulate the game.
 */
interface GameHandler<Turn : Any> {
    fun getNimber(): Nimber
    fun isTurnCorrect(turn: Turn): Boolean
    fun getTurnTo(to: Nimber): Turn
    fun getWinningTurn(): Turn = getTurnTo(NimZero)
    fun processTurn(turn: Turn)
    fun processWinningTurn(turn: Turn) = processTurn(getWinningTurn())
}