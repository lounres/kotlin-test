package math.varia.games

/**
 * Specifies what any [impartial game](https://en.wikipedia.org/wiki/Impartial_game) should be able to do.
 */
interface ImpartialGame {
    /**
     * Get nimber of the game.
     */
    fun getNimber(): Nimber
}