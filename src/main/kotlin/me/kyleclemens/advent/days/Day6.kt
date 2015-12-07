package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import kotlin.text.Regex

@UsesData(day = 6)
open class Day6 : Solution {

    /**
     * A grid, or two-dimensional array, of type [T].
     */
    private abstract class Grid<T> {
        /**
         * The two-dimensional array of type [T].
         */
        abstract val grid: Array<Array<T>>

        /**
         * Processes [instruction] and returns the outcome given [value].
         */
        abstract fun processInstruction(instruction: Instruction, value: T): T

        /**
         * Processes [instructionWithPoints].
         */
        fun processInstruction(instructionWithPoints: InstructionWithPoints) {
            val p1 = instructionWithPoints.pointOne
            val p2 = instructionWithPoints.pointTwo
            // Loop between the two points
            for (x in p1.x..p2.x) {
                for (y in p1.y..p2.y) {
                    // Change the value at the point to the result of the instruction
                    this.grid[x][y] = this.processInstruction(instructionWithPoints.instruction, this.grid[x][y])
                }
            }
        }

    }

    /**
     * A boolean-based grid.
     */
    private class OnOffGrid : Grid<Boolean>() {
        override val grid = Array(1000, { BooleanArray(1000).toTypedArray() })

        override fun processInstruction(instruction: Instruction, value: Boolean) = instruction.process(value)
    }

    /**
     * An int-based grid.
     */
    private class BrightnessGrid : Grid<Int>() {
        override val grid = Array(1000, { IntArray(1000).toTypedArray() })

        override fun processInstruction(instruction: Instruction, value: Int) = instruction.process(value)
    }

    /**
     * A two-dimensional point.
     */
    private data class Point(val x: Int, val y: Int)

    /**
     * An [Instruction] to be applied to all points between [pointOne] and [pointTwo].
     */
    private data class InstructionWithPoints(val instruction: Instruction, val pointOne: Point, val pointTwo: Point)

    /**
     * Converts this string to an [InstructionWithPoints].
     */
    private fun String.toInstruction(): InstructionWithPoints {
        // Determine the type of instruction based on how the string starts
        val instruction = when {
            this.startsWith("turn off") -> Instruction.OFF
            this.startsWith("turn on") -> Instruction.ON
            this.startsWith("toggle") -> Instruction.TOGGLE
            else -> throw IllegalStateException("Invalid instruction")
        }
        // Find the point between the two points
        val halfMarker = this.indexOf(' ') + this.indexOf(',')
        // Find the coordinates for point one
        val (p1x, p1y) = this.substring(0, halfMarker).replace(Regex("[^\\d,]"), "").split(",").map { it.toInt() }
        // Find the coordinates for point two
        val (p2x, p2y) = this.substring(halfMarker + 1, this.length).let { it.substring(it.indexOf(' ') + 1, it.length) }.split(",").map { it.toInt() }
        // Return the instruction with the two points
        return InstructionWithPoints(instruction, Point(p1x, p1y), Point(p2x, p2y))
    }

    /**
     * Possible instructions.
     */
    private enum class Instruction {
        OFF {
            override fun process(boolean: Boolean): Boolean {
                // Always off
                return false
            }

            override fun process(int: Int): Int {
                // Subtract one from the value with a minimum of 0
                return Math.max(0, int - 1)
            }
        },
        ON {
            override fun process(boolean: Boolean): Boolean {
                // Always on
                return true
            }

            override fun process(int: Int): Int {
                // Add one to the value
                return int + 1
            }
        },
        TOGGLE {
            override fun process(boolean: Boolean): Boolean {
                // Flip the boolean
                return !boolean
            }

            override fun process(int: Int): Int {
                // Add two to the value
                return int + 2
            }
        };

        abstract fun process(boolean: Boolean): Boolean
        abstract fun process(int: Int): Int
    }

    private fun produceFirstAnswer(): Int {
        // Make a new boolean grid
        val grid = OnOffGrid()
        // Convert the data to instructions, and process each instruction with the grid
        this.splitData.map { it.toInstruction() }.forEach { grid.processInstruction(it) }
        // Sum the number of true cells
        return grid.grid.sumBy { it.count { boolean -> boolean } }
    }

    private fun produceSecondAnswer(): Int {
        // Make a new int grid
        val grid = BrightnessGrid()
        // Convert the data to instructions, and process each instruction with the grid
        this.splitData.map { it.toInstruction() }.forEach { grid.processInstruction(it) }
        // Sum the value of all cells
        return grid.grid.sumBy { it.sum() }
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()

}
