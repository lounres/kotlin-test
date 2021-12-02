package math.varia.games


class Mexer() {
    var result = 0u
        private set

    private val greaters = sortedSetOf<UInt>()

    private fun updateResult() {
        while (greaters.firstOrNull() == result) {
            result = greaters.pollFirst()!! + 1u
        }
    }

    fun add(value: UInt) {
        if (value == result) {
            result += 1u
            updateResult()
        } else if (value > result) greaters.add(value)
    }

    fun addAll(elements: Collection<UInt>) {
        greaters.addAll(elements.asSequence().filter { it > result })
        if (result in elements) updateResult()
    }

    fun addAll(elements: Sequence<UInt>) {
        greaters.addAll(elements.filter { it > result })
        if (result in elements) updateResult()
    }

    constructor(elements: Collection<UInt>) : this() { addAll(elements) }
    constructor(elements: Sequence<UInt>) : this() { addAll(elements) }
}

fun mex(nums: Collection<UInt>): UInt = Mexer(nums).result

fun mex(nums: Sequence<UInt>): UInt = Mexer(nums).result

@kotlin.ExperimentalUnsignedTypes
fun mex(vararg nums: UInt): UInt = Mexer(nums.asSequence()).result

fun mex(nims: Collection<Nimber>): Nimber = mex(nims.asSequence().map { it.value }).toNimber()

fun mex(nims: Sequence<Nimber>): Nimber = mex(nims.asSequence().map { it.value }).toNimber()

fun mex(vararg nims: Nimber): Nimber = mex(nims.asSequence().map { it.value }).toNimber()

@JvmName("mexExtension")
fun Collection<UInt>.mex(): UInt = mex(this)

@JvmName("mexExtension")
fun Sequence<UInt>.mex(): UInt = mex(this)

@JvmName("mexExtension")
fun Collection<Nimber>.mex(): Nimber = mex(this)

@JvmName("mexExtension")
fun Sequence<Nimber>.mex(): Nimber = mex(this)