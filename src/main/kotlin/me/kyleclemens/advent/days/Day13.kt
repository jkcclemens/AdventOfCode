package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 13)
open class Day13 : Solution {

    private class TableHappiness {
        /**
         * Map of the relationships for everyone. To find Jane's relationship to Joe, use `map["Jane"]["Joe"]`.
         */
        val relationships = hashMapOf<String, MutableMap<String, Int>>()
        /**
         * A list of individuals seated at the table, which is generated from [Map.keys] of [relationships].
         */
        val individuals: List<String>
            get() = this.relationships.keys.toList()
    }

    /**
     * Converts this String list into a [TableHappiness] instance.
     */
    private fun List<String>.asTableHappiness(): TableHappiness {
        // Make the relationships map
        val relationships = hashMapOf<String, MutableMap<String, Int>>()
        // Iterate through each relationship statement
        for (item in this) {
            // Split it by spaces
            val split = item.split(" ")
            // Get the key
            val key = split[0]
            // Get the value or make one if not present
            val value = relationships[key] ?: hashMapOf()
            // Get the other person in this relationship (and remove the period from the end of the sentence)
            val other = split.last().let { it.substring(0, it.length - 1) }
            // Get the amount and make it negative if the second word is "lose"
            val amount = split[3].toInt().let { if (split[2] == "lose") -it else it }
            // Assign the relationship
            value[other] = amount
            // Assign relationships
            relationships[key] = value
        }
        // Create a new table
        val table = TableHappiness()
        // Add our relationships
        table.relationships += relationships
        // Return the resulting table
        return table
    }

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

    private fun getPossibleHappiness(table: TableHappiness = this.splitData.asTableHappiness()): Sequence<Int> {
        // Generate the permutations for the seating arrangements
        return table.individuals.permutations
            // Map each arrangement to its total happiness
            .map { arrangement ->
                arrangement
                    // Map each person to a pair of their happiness with the person to their left and right
                    .mapIndexed { seat, person ->
                        // Make sure to wrap around
                        val left = arrangement[if (seat == 0) arrangement.size - 1 else seat - 1]
                        val right = arrangement[if (seat == arrangement.size - 1) 0 else seat + 1]
                        val relationships = table.relationships[person]!!
                        return@mapIndexed relationships[left]!! to relationships[right]!!
                    }
                    // Flat map the results
                    .flatMap { it.toList() }
                    // Sum the results
                    .sum()
            }
    }

    private fun produceFirstAnswer(): Int {
        // Get the maximum possible happiness
        return this.getPossibleHappiness().max()!!
    }

    // FIXME: Does not produce the correct answer
    private fun produceSecondAnswer(): Int {
        // Convert existing data into a TableHappiness instance
        val table = this.splitData.asTableHappiness()
        // Define a name for myself
        val me = "Me"
        // Add a 0 happiness relationship with me for everyone
        table.individuals.forEach {
            table.relationships[it]!![me] = 0
        }
        // Add a 0 happiness relationship with everyone for me
        table.relationships[me] = table.individuals.toMap({ it }, { 0 }).toLinkedMap()
        // The question on Advent of Code is vague. Using a negative answer didn't say that it was too high or too low,
        // so I assume that they want a positive answer. Either way, subtract the maximum possible happiness and the
        // first answer.
        return Math.abs(this.getPossibleHappiness(table).max()!! - this.produceFirstAnswer())
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()

}