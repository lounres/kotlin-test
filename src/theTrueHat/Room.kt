package theTrueHat

data class Room(val key: String, val settings: Settings) {
    data class Settings(
        var delayTime: Int,
        var explanationTime: Int,
        var aftermathTime: Int,
        var strictMode: Boolean,
        var termCondition: TermCondition,
        var wordsetType: WordsetType,
        var dictionaryId: Int,
        var wordsNumber: Int,
        var roundsNumber: Int,
        var dictionaryFileInfo: String,
        var wordset: ArrayList<String>,
        var fixedPairs: Boolean,
        var pairMatching: PairMatching
    ) {
        enum class TermCondition {
            words, rounds
        }
        enum class WordsetType {
            serverDictionary, hostDictionary, playerWords
        }
        enum class PairMatching {
            random, host
        }
//        constructor() : this(
//            3000,
//            4000,
//            3000,
//            false,
//            TermCondition.words,
//            WordsetType.serverDictionary,
//            0,
//            100,
//            10,
//            "",
//            ArrayList<String>(0),
//            false,
//            PairMatching.random
//        )
    }
    enum class Stage{
        wait,
        prepare_wordCollection,
        prepare_pairMatching,
        play_wait,
        play_explanation,
        play_edit,
        end;
    }
    data class HostDictionary (
        var words: ArrayList<String>,
        var name: HashMap<String, String>
    ) {
        val wordsNumber
            get() = words.size
    }
    data class EditRecord (
        val word: String,
        val wordState: String,
        val transfer: Boolean
    )
    data class ExplanationRecord (
        var from: Int,
        var to: Int,
        var word: String,
        var time: Int,
        var extra_time: Int
    )

    var stage = Stage.wait
    val users = ArrayList<User>()
    val pairs = ArrayList<Pair<Int, Int>>()
    lateinit var nextPlayers: ArrayList<Int>
    val hostDictionary = HostDictionary(ArrayList(), hashMapOf("ru" to "Словарь хоста", "en" to "Host's dictionary"))

    lateinit var freshWords: ArrayList<String>
    val usedWords = HashMap<String, String>()
    var speaker = 0
    var listener = 0
    var speakerReady = false
    var listenerReady = false
    var word = ""
    var startTime = 0
    val editWords = ArrayList<EditRecord>()
    var numberOfTurn = 0
    var numberOfLap = 0
    val explanationRecords = ArrayList<ArrayList<ExplanationRecord>>()
    var start_timestamp = 0
    var end_timestamp = 0
    var explStartMainTime = 0
    var explStartExtraTime = 0


    /**
     * Returns playersList structure
     *
     * @return list of players
     */
    fun getPlayersList() = users.map { hashMapOf("username" to it.username, "online" to it.online) }

    /**
     * Returns pairs structure
     *
     * @return list of players
     */
    fun getPairs() = pairs.map { Pair(users[it.first].username, users[it.second].username) }


    /**
     * Returns host username
     *
     * @return host username
     */
    fun getHostUsername() = users.find { it.online } ?: ""

    /**
     * Get next speaker and listener
     *
     * @param lastSpeaker Index of previous speaker
     * @param lastListener Index of previous listener
     * @return object with fields: speaker and listener -- indices of speaker and listener
     */
    fun getNextPair(lastSpeaker: Int, lastListener: Int) =
        if (settings.fixedPairs) {
            Pair(nextPlayers[lastSpeaker], nextPlayers[lastListener])
        } else {
            getNextCircledPair(lastSpeaker, lastListener)
        }

    fun getNextPair(pair: Pair<Int, Int>) = getNextPair(pair.first, pair.second)

    fun getNextPair() = getNextPair(speaker, listener)

    /**
     * Get next speaker and listener in circle (everyone-to-everyone) mode
     *
     * @param lastSpeaker Index of previous speaker
     * @param lastListener Index of previous listener
     * @return object with fields: speaker and listener --- indices of speaker and listener
     */
    fun getNextCircledPair(lastSpeaker: Int, lastListener: Int): Pair<Int, Int> {
        val speaker = (lastSpeaker + 1) % users.size
        var listener = (lastListener + 1) % users.size
        if (speaker == 0) {
            listener = (listener + 1) % users.size
            if (listener == speaker) {
                listener++
            }
        }
        return Pair(speaker, listener)
    }

    fun getNextCircledPair(pair: Pair<Int, Int>) = getNextCircledPair(pair.first, pair.second)

    fun getNextCircledPair() = getNextCircledPair(speaker, listener)

    /**
     * Get timetable for N rounds
     *
     * @return Array of speakers' and listeners' names
     */
    fun getTimetable(): ArrayList<Pair<String, String>> {
        val timetable = ArrayList<Pair<String, String>>()
        val timetableDepth = if (settings.fixedPairs) users.size / 2 else 2 * users.size - 1
        var roundsLeft = settings.roundsNumber - numberOfLap
        var obj = Pair(speaker, listener)
        for (i in 0 until timetableDepth) {
            if (roundsLeft == 0 && settings.termCondition == Settings.TermCondition.rounds) break
            timetable.add(Pair(users[obj.first].username, users[obj.second].username))
            obj = getNextPair(obj)
            if ((!settings.fixedPairs && obj.first == 0 && obj.second == 1) ||
                (settings.fixedPairs && obj.first == pairs[0].first && obj.second == pairs[0].second))
                --roundsLeft
        }
        return timetable
    }
}