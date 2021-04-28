//package theTrueHat
//
/////**
//// * Checks object with the pattern
//// *
//// * @param object --- object
//// * @param pattern --- pattern
//// * @return if objects corresponds to the pattern
//// */
////fun checkObject(object, pattern) {
////    // checking object for undefined or null
////    if (object == undefined || object == null) {
////        return false;
////    }
////
////    // comparing length
////    val objKeys = Object.keys(object);
////    if (objKeys.length !== Object.keys(pattern).length) {
////        return false;
////    }
////
////    for (var i = 0; i < objKeys.length; ++i) {
////        if (!(objKeys[i] in pattern)) {
////            return false;
////        }
////        val typeStr = pattern[objKeys[i]];
////        if (typeof(object[objKeys[i]]) !== typeStr) {
////        return false;
////    }
////    }
////    return true;
////}
//
///**
// * Generate free key
// *
// * @return free key (random one)
// */
//fun genFreeKey(): String {
//    // getting the settings
//    val minKeyLength = config.minKeyLength
//    val maxKeyLength = config.maxKeyLength
//    val keyConsonant = config.keyConsonant
//    val keyVowels = config.keyVowels
//    // getting the key length
//    val keyLength = randrange(minKeyLength, maxKeyLength + 1)
//    // generating the key
//    var key = ""
//    for (i in 0 until keyLength) {
//        val charList: ArrayList<String> = if (i % 2 == 0) keyConsonant else keyVowels
//        key += charList[randrange(charList.size)]
//    }
//    return key
//}
//
/////**
//// * Returns random number from interval [a, b)
//// *
//// * @param a lower bound
//// * @param b upper bound
//// * @return random integer from [a, b)
//// */
////fun randrange(a = 0, b = 0) {
////    if (arguments.length == 1) {
////        b = a;
////        a = 0;
////    }
////    return Math.floor(a + (b - a) * Math.random());
////}
//
/////**
//// * Finds first position in users array where element has attribute with given value
//// *
//// * @param users The users array
//// * @param field The attribute to check
//// * @param val the value to find
//// * @return position if exists else -1
//// */
////fun findFirstPos(users, field, val) {
////    for (var i = 0; i < users.length; ++i) {
////        if (users[i][field] == val) {
////                return i;
////            }
////    }
////    return -1;
////}
//
/////**
//// * Finds first position in users array where given socket ID is in list of socket IDs
//// *
//// * @param users The users array
//// * @param sid Socket ID
//// * @return position if exists else -1
//// */
////fun findFirstSidPos(users, sid) {
////    for (var i = 0; i < users.length; ++i) {
////        if (users[i]["sids"][0] == sid) {
////            return i;
////        }
////    }
////    return -1;
////}
//
/////**
//// * Returns current player's room.
//// *
//// * @param socket The socket of the player
//// * @return id of current player's room: his own socket room or game room with him
//// */
////fun getRoomKey(socket) {
////    val sid = socket.id;
////    val roomsList = Object.keys(socket.rooms);
////    // Searching for the game room with the user
////    for (var i = 0; i < roomsList.length; ++i) {
////        if (roomsList[i] !== sid) {
////            return roomsList[i]; // It's found and returning
////        }
////    }
////    return socket.id; // Nothing found. User's own room is returning
////}
//
///**
// * Start an explanation
// *
// * @param key Key of the room
// */
//fun startExplanation(key) {
//    rooms[key].stage = Room.Stage.play_explanation
//    val currentTime = (new Date()).getTime()
//    rooms[key].startTime = currentTime + (rooms[key].settings.delayTime + TRANSFER_TIME)
//    rooms[key].word = rooms[key].freshWords.pop()
//
//    if (rooms[key].settings.strictMode) {
//        val numberOfTurn = rooms[key].numberOfTurn
//        setTimeout(fun() {
//            // if explanation hasn't finished yet
//            if (!( key in rooms)) {
//                return;
//            }
//            if (rooms[key].numberOfTurn == numberOfTurn) {
//                finishExplanation(key);
//            }
//        }, (rooms[key].settings.delayTime + rooms[key].settings.explanationTime + rooms[key].settings.aftermathTime + TRANSFER_TIME))
//    }
//    setTimeout(() => Signals.sNewWord(key), TRANSFER_TIME)
//    rooms[key].explStartMainTime = rooms[key].startTime
//    rooms[key].explStartExtraTime = rooms[key].startTime + rooms[key].settings.explanationTime
//    rooms[key].explanationRecords.push([])
//    Signals.sExplanationStarted(key)
//}
//
///**
// * Finish an explanation
// *
// * @param key --- key of the room
// */
//fun finishExplanation(key) {
//    // if game has ended
//    if (!(key in rooms)) return
//
//    // if signal has been sent
//    if (rooms[key].stage != Room.Stage.play_explanation) return
//    rooms[key].stage = Room.Stage.play_edit
//
//    if (rooms[key].word != "") {
//        rooms[key].editWords.add(Room.EditRecord(
//            rooms[key].word,
//            "notExplained",
//            true
//        ))
//        val currentTime = (new Date()).getTime()
//        rooms[key].explanationRecords[rooms[key].explanationRecords.length - 1].push(Room.ExplanationRecord(
//            rooms[key].speaker,
//            rooms[key].listener,
//            rooms[key].word,
//            currentTime - rooms[key].explStartMainTime,
//            if (currentTime >= rooms[key].explStartExtraTime) currentTime - rooms[key].explStartExtraTime else 0
//        ))
//    }
//
//    rooms[key].startTime = 0
//    rooms[key].word = ""
//
//    Signals.sExplanationEnded(key)
//
//    // generating editWords for client (without 'transport' flag)
//    var editWords = ArrayList<Room.EditRecord>()
//    for (i in 0 until rooms[key].editWords.size) {
//        editWords.add(Room.EditRecord(
//            rooms[key].editWords[i].word,
//            rooms[key].editWords[i].wordState,
//            true
//        ))
//    }
//
//    Signals.sWordsToEdit(key, editWords)
//}
//
///**
// * End the game
// *
// * @param key --- key of the room
// */
//fun endGame(key) {
//    // recording time
//    rooms[key].end_timestamp = (new Date()).getTime();
//
//    // preparing results
//    var results = ArrayList<>()
//    for (i in 0 until rooms[key].users.length) {
//        results.add({
//            "username": rooms[key].users[i].username,
//            "scoreExplained": rooms[key].users[i].scoreExplained,
//            "scoreGuessed": rooms[key].users[i].scoreGuessed
//        })
//    }
//
//    // sorting results
//    results.sort { (a, b) -> 0 - (a.scoreExplained + a.scoreGuessed - b.scoreExplained - b.scoreGuessed) }
//
//    // preparing key for next game
//    val nextKey = genFreeKey()
//
//    // copying users and settings to next room
//    rooms[nextKey] = Room(nextKey, Object.assign({}, rooms[key].settings))
//    rooms[nextKey].users = rooms[key].users
//    rooms[nextKey].hostDictionary = rooms[key].hostDictionary
//    for (i in 0 until rooms[nextKey].users.length) {
//        rooms[nextKey].users[i].sids.clear()
//        rooms[nextKey].users[i].online = false
//        rooms[nextKey].users[i].scoreExplained = 0
//        rooms[nextKey].users[i].scoreGuessed = 0
//    }
//
//    Signals.sGameEnded(key, results, nextKey)
//
//    // sending statistics
//    sendStat(Object.assign({}, rooms[key]))
//
//    // removing room
//    delete rooms[key]
//
//    // removing users from room
//    io.sockets.in(key).clients(fun(err, clients) {
//        clients.forEach(fun(sid) {
//            val socket = io.sockets.connected[sid]
//            socket.leave(key)
//        })
//    })
//}
//
///**
// * Send statistics
// *
// * @param room --- room object
// */
//fun sendStat(room: Room) {
//    var sendObject = HashMap<String, Any>(0)
//    sendObject["version"] = config.protocolVersion
//    sendObject["app"] = hashMapOf(
//        "name" to config.appName,
//        "version" to version.version
//    )
//    sendObject["mode"] = config.mode
//    sendObject["start_timestamp"] = room.start_timestamp
//    sendObject["end_timestamp"] = room.end_timestamp
//    sendObject["player_time_zone_offsets"] = room.users.map {it.timeZoneOffset}
//    sendObject["attempts"] = ArrayList<Room.ExplanationRecord>()
//    for (i in 0 until room.explanationRecords.size) {
//        for (j in 0 until room.explanationRecords[i].size) {
//        room.explanationRecords[i][j].time -= room.explanationRecords[i][j].extra_time
//        sendObject["attempts"].add(room.explanationRecords[i][j])
//    }
//    }
//
//    // sending data
//    console.log("Send:")
//    console.log(sendObject)
//    fetch(Object.assign({}, config.statSendConfig, {"data": sendObject}))
//        .catch((err) => console.warn(err))
//}
//
///**
// * Preparing room for the game
// */
//fun gamePrepare(room: Room) {
//    // generating word list
//    generateWords(room)
//
//    // setting number of turn
//    room.numberOfTurn = 0
//    room.numberOfLap = -1
//
//    val numberOfPlayers = room.users.size
//    room.speaker = numberOfPlayers - 1;
//    room.listener = numberOfPlayers - 2;
//    if (room.settings.fixedPairs) {
//        room.nextPlayers = ArrayList(room.pairs.size)
//        for (i in 1 until room.pairs.size) {
//            room.nextPlayers[room.pairs[i-1].first] = room.pairs[i].first
//            room.nextPlayers[room.pairs[i-1].second] = room.pairs[i].second
//        }
//        room.nextPlayers[room.pairs.last().first] = room.pairs[0].second
//        room.nextPlayers[room.pairs.last().second] = room.pairs[0].first
//    }
//
//    if (!roundPrepare(room)) return
//
//    // recording time
//    room.start_timestamp = (new Date()).getTime()
//
//    Signals.sGameStarted(room.key)
//}
//
///**
// * Preparing room for new round
// */
//fun roundPrepare(room: Room): Boolean {
//    // if no words left it's time to finish the game
//    if (room.freshWords.size == 0) {
//        endGame(room.key)
//        return false
//    }
//
//    // setting stage to 'play_wait'
//    room.stage = Room.Stage.play_wait
//
//    // preparing storage for words to edit
//    room.editWords.clear()
//
//    // preparing word container
//    room.word = ""
//
//    // preparing startTime container
//    room.startTime = 0
//
//    // preparing flags for 'wait'
//    room.speakerReady = false
//    room.listenerReady = false
//
//    // updating number of the turn
//    room.numberOfTurn++
//
//    // preparing 'speaker' and 'listener'
//    val nextPair = room.getNextPair(room.speaker, room.listener)
//    room.speaker = nextPair.first
//    room.listener = nextPair.second
//
//    if (room.speaker == 0 && room.listener == 1) room.numberOfLap++
//
//    if (room.settings.termCondition == Room.Settings.TermCondition.rounds && room.numberOfLap == room.settings.roundsNumber) {
//        endGame(room.key)
//        return false
//    }
//
//    return true
//}
//
///**
// * Generate word list
// *
// * @return list of words
// */
//fun generateWords(room: Room) {
//    val settings = room.settings
//    val dict = when (settings.wordsetType) {
//        Room.Settings.WordsetType.serverDictionary -> dicts[settings.dictionaryId]
//        Room.Settings.WordsetType.hostDictionary -> room.hostDictionary
//        Room.Settings.WordsetType.playerWords -> {
//            var wordList = ArrayList<String>()
//            for (i in 0 until room.users.size) {
//                    val user = room.users[i]
//                    wordList.addAll(user.userWords)
//                    user.userReady = false
//                    user.userWords.clear()
//                }
//
//            wordList = ArrayList(HashSet(wordList))
//
//            settings.wordsNumber = Math.min(dict.wordsNumber, config.settingsRange.wordsNumber.max - 1)
//
//            // checking number of words
//            if (wordList.size > settings.wordsNumber) Signals.sFailure(room.key, "cWordsReady", null, "Количество слов уменьшено до максимально возможного")
//
//            {
//                words: wordList,
//                wordsNumber: wordList.length,
//                name: "Players' dictionary"
//            }
//        }
//    }
//    val words = ArrayList<String>()
//    val used = HashSet<Int>()
//    val numberOfAllWords = dict.wordsNumber
//    val wordsNumber = settings.wordsNumber
//    while (words.size < wordsNumber) {
//        val pos = randrange(numberOfAllWords)
//        if (!(pos in used)) {
//            used.add(pos)
//            words.add(dict.words[pos])
//        }
//    }
//    room.freshWords =  words
//}