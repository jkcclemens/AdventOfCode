package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 3)
open class Day3 : Solution {

    private class TwoDimensionalHouseGrid {
        /**
         * A map of the houses, referenced by position in a "X,Y" format, with keys being the number of presents
         */
        val houses = hashMapOf<String, Int>()
        /**
         * The current coordinates in the correct format
         */
        val currentCoordinates: String
            get() = "${currentX},$currentY"
        /**
         * Current X position
         */
        var currentX = 0
        /**
         * Current Y position
         */
        var currentY = 0

        /**
         * Delivers a present to the house at [currentX] and [currentY].
         */
        fun deliverPresent() {
            this.houses[this.currentCoordinates] = (this.houses[this.currentCoordinates] ?: 0) + 1
        }

        fun processMove(c: Char) {
            // Ignore newlines
            if (c == '\n' || c == '\r') return
            when (c) {
                'v' -> {
                    // Move down if a down arrow
                    this.currentY--
                }
                '^' -> {
                    // Move up if an up arrow
                    this.currentY++
                }
                '>' -> {
                    // Move right if a right arrow
                    this.currentX++
                }
                '<' -> {
                    // Move left if a left arrow
                    this.currentX--
                }
                else -> throw IllegalStateException("Invalid data")
            }
            // "After each move, he delivers another present to the house at his new location."
            this.deliverPresent()
        }

        fun processMoves(chars: CharArray) {
            // Process each move
            chars.forEach { this.processMove(it) }
        }

        init {
            this.deliverPresent() // "He begins by delivering a present to the house at his starting location"
        }
    }

    private fun produceFirstAnswer(): Int {
        // Make a new grid for Santa to move about in
        val grid = TwoDimensionalHouseGrid()
        // Loop through the instructions he got
        grid.processMoves(this.getData().toCharArray())
        // Since the grid only contains houses that have received presents, return the size of the grid
        return grid.houses.size
    }

    private fun produceSecondAnswer(): Int {
        // Make a new grid for Santa
        val santaGrid = TwoDimensionalHouseGrid()
        // Make a new grid for Robo-Santa
        val roboSantaGrid = TwoDimensionalHouseGrid()
        // Loop through the indexed instructions
        for (item in this.getData().withIndex()) {
            if (item.index % 2 == 0) {
                // If it's an even-indexed instruction, give it to Santa
                santaGrid.processMove(item.value)
            } else {
                // If it's an odd-indexed instruction, give it to Robo-Santa
                roboSantaGrid.processMove(item.value)
            }
        }
        // Merge their grids and count the amount of houses that received presents
        return (santaGrid.houses + roboSantaGrid.houses).size
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
