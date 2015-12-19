package me.kyleclemens.advent.helpers

// Lifted from https://github.com/kotlin-projects/kotlin-euler
val <T : Any> List<T>.permutations: Sequence<List<T>>
    get() = if (size == 1) sequenceOf(this) else {
        val iterator = iterator()
        var head = iterator.next()
        var permutations = (this - head).permutations.iterator()
        fun nextPermutation(): List<T>? = if (permutations.hasNext()) permutations.next() + head else {
            if (iterator.hasNext()) {
                head = iterator.next()
                permutations = (this - head).permutations.iterator()
                nextPermutation()
            } else null
        }
        sequence { nextPermutation() }
    }

fun Array<BooleanArray>.neighbors(row: Int, col: Int): Map<Int, Boolean> {
    fun Array<BooleanArray>.gett(index: Int): BooleanArray {
        return this.getOrElse(index) { BooleanArray(0) }
    }

    fun BooleanArray.gett(index: Int): Boolean {
        return this.getOrElse(index) { false }
    }
    return mapOf(
        1 to this.gett(row - 1).gett(col - 1),
        2 to this.gett(row - 1).gett(col),
        3 to this.gett(row - 1).gett(col + 1),
        4 to this.gett(row).gett(col + 1),
        5 to this.gett(row + 1).gett(col + 1),
        6 to this.gett(row + 1).gett(col),
        7 to this.gett(row + 1).gett(col - 1),
        8 to this.gett(row).gett(col - 1)
    )
}
