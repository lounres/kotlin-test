package math.varia.games

/**
 * Describes games that can be emulated.
 */
interface HandleableGame {
    /**
     * Returns game's handler.
     */
    fun getHandler(): GameHandler<*>
}