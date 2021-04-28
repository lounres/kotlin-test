package math.graph

fun bfs(n: Int, edges: List<Pair<Int, Int>>, starts: List<Int>): Pair<List<List<Int>>, List<Int>> {
    val adjacency = List<MutableSet<Int>>(n) { HashSet() }.apply {
        for (edge in edges) {
            this[edge.first].add(edge.second)
            this[edge.second].add(edge.first)
        }
    }
    val levels: ArrayList<List<Int>> = arrayListOf(starts)
    val whatLevel = MutableList(n) { 0 }
    var currLevel = 0
    var prev: Set<Int>
    var curr: Set<Int> = HashSet()
    var next: Set<Int> = starts.toMutableSet()

    while (next.isNotEmpty()) {
        currLevel ++
        prev = curr
        curr = next
        next = curr
            .map { adjacency[it] }
            .reduce<Set<Int>, MutableSet<Int>> { acc, mutableSet -> mutableSet.union(acc) }
            .subtract(prev)
        levels.add(curr.toList())
        curr.map { whatLevel[it] = currLevel }
    }

    return Pair(levels, whatLevel)
}

fun distances(n: Int, edges: List<Pair<Int, Int>>): List<List<Int>> {
    val adjacency = List<MutableSet<Int>>(n) { HashSet() }
    val distance: List<MutableList<Int>> = MutableList(n) { MutableList(n) { 0 } }

    fun update(edge: Pair<Int, Int>) {
        val (v1, v2) = edge

        for (u in 0 until n) {

            if (distance[v1][u] > distance[v1][v2] + distance[v2][u]) {
                distance[v1][u] = distance[v1][v2] + distance[v2][u]
                update(Pair(v1, u))
            }
            if (distance[v2][u] > distance[v2][v1] + distance[v1][u]) {
                distance[v2][u] = distance[v2][v1] + distance[v1][u]
                update(Pair(v2, u))
            }
        }
    }

    for (edge in edges) {
        val (v1, v2) = edge

        adjacency[v1].add(v2)
        adjacency[v2].add(v1)

        distance[v1][v2] = 1
        distance[v2][v1] = 1

        update(edge)
    }

    return distance
}