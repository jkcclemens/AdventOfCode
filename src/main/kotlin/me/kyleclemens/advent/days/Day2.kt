package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 2)
open class Day2 : Solution {

    private data class Dimensions(val length: Int, val width: Int, val height: Int)

    private fun String.toDimensions(): Dimensions {
        // The string should have two 'x's if it is a valid Dimensions
        require(this.count { it == 'x' } == 2)
        // Split the string by 'x', convert the results to ints, then convert them to a Dimensions object
        return this.split('x').map { it.toInt() }.run { Dimensions(this[0], this[1], this[2]) }
    }

    private fun getDimensions(): List<Dimensions> {
        // Get the data for this day, split it by lines, filter out empty lines, convert each line to a Dimensions
        return this.splitData.map { it.toDimensions() }
    }

    private fun produceFirstAnswer(): Int {
        return this.getDimensions().map { dimension ->
            // Do the necessary calculations
            val side1 = 2 * dimension.length * dimension.width
            val side2 = 2 * dimension.width * dimension.height
            val side3 = 2 * dimension.height * dimension.length
            // Find the smallest and divide it by two to find the slack (these are all multiplied by two because the
            // faces directly across from them are the same, and this is a 6-sided object)
            val smallest = listOf(side1, side2, side3).min()!! / 2
            // Add all the surface area and the slack
            return@map side1 + side2 + side3 + smallest
        }.sum() // sum the results
    }

    private fun produceSecondAnswer(): Int {
        return this.getDimensions().map { dimension ->
            // Find the volume
            val volume = dimension.height * dimension.width * dimension.length
            // Do the necessary calculations
            val side1 = dimension.height * 2
            val side2 = dimension.length * 2
            val side3 = dimension.width * 2
            // Check each perimeter and find the smallest one
            val smallest = listOf(side1 + side2, side1 + side3, side2 + side1, side2 + side3, side3 + side1, side3 + side2).min()!!
            // Add the volume and the smallest perimeter
            return@map volume + smallest
        }.sum()
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
