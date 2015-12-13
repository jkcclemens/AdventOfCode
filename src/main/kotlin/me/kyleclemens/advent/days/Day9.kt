package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 9)
open class Day9 : Solution {

    // Lifted from https://github.com/kotlin-projects/kotlin-euler
    private val <T : Any> List<T>.permutations: Sequence<List<T>>
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

    private fun findAllDistances(): List<Int> {
        val distances = this.splitData.toMap({ it.split(" = ")[0].split(" to ").toSet() }, { it.split(" = ")[1].toInt() })
        val destinations = this.splitData.flatMap { it.split(" = ")[0].split(" to ") }.distinct()
        return destinations.permutations
            .map {
                it
                    .mapIndexed { i, s ->
                        if (i == it.size - 1) 0
                        else distances[setOf(s, it[i + 1])]!!
                    }
                    .sum()
            }
            .toList()
    }


    private fun produceFirstAnswer() = this.findAllDistances().min()!!

    private fun produceSecondAnswer() = this.findAllDistances().max()!!

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
