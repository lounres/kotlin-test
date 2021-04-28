package theTrueHat

data class User(
    val username: String,
    val sids: ArrayList<String>,
    var online: Boolean = true,
    val timeZoneOffset: Int
) {
    var scoreExplained = 0
    var scoreGuessed = 0
    lateinit var userWords: ArrayList<String>
    var userReady: Boolean = false
}