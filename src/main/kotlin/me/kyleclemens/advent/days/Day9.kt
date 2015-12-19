package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import me.kyleclemens.advent.helpers.permutations

@UsesData(day = 9)
open class Day9 : Solution {

    private fun findAllDistances(): List<Int> {
        // Make a map of distances from the input. The map has a set paired to an int, since London to Dublin is the
        // same distance as Dublin to London.
        val distances = this.splitData.toMap({ it.split(" = ")[0].split(" to ").toSet() }, { it.split(" = ")[1].toInt() })
        // Make a list of possible destinations
        val destinations = this.splitData.flatMap { it.split(" = ")[0].split(" to ") }.distinct()
        return destinations.permutations
            // Map the each permutation of the destination list to its total distance
            .map {
                it
                    // Map this destination to its distance from the destination in front of it
                    .mapIndexed { i, s ->
                        // If we've reached the end of the list, the distance is 0, since we've already done the math
                        // for this destination
                        if (i == it.size - 1) 0
                        // Otherwise, get the distance from the set of this destination and the next one
                        else distances[setOf(s, it[i + 1])]!!
                    }
                    // Sum the distances
                    .sum()
            }
            // Convert the sequence to a list
            .toList()
    }


    private fun produceFirstAnswer() = this.findAllDistances().min()!! // Get the shortest route

    private fun produceSecondAnswer() = this.findAllDistances().max()!! // Get the longest route

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
