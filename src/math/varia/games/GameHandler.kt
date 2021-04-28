package math.varia.games

open class GameHandler(val mainGame: Game) {
    val sortedVertexList = ArrayList<Game>()
    val leaves = mutableSetOf<Game>()

    init {
        updateFrom(mainGame)
    }

    fun updateFrom(game: Game) {
        val toCheck = arrayListOf(game)
        while (toCheck.isNotEmpty()) {
            val now = toCheck[0]
            toCheck.removeAt(0)
            sortedVertexList.addAll(now.subgames subtract sortedVertexList)
            if (now.subgames.isEmpty()) leaves.add(now) else leaves.remove(now)
        }
    }
}