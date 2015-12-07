package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import kotlin.text.Regex

@UsesData(day = 6)
open class Day6 : Solution {

    private abstract class Grid<T> {
        abstract val grid: Array<Array<T>>
        abstract fun processInstruction(instruction: Instruction, value: T): T

        fun processInstruction(instructionWithPoints: InstructionWithPoints) {
            val p1 = instructionWithPoints.pointOne
            val p2 = instructionWithPoints.pointTwo
            for (x in p1.x..p2.x) {
                for (y in p1.y..p2.y) {
                    this.grid[x][y] = this.processInstruction(instructionWithPoints.instruction, this.grid[x][y])
                }
            }
        }

    }

    private class OnOffGrid : Grid<Boolean>() {
        override val grid = Array(1000, { BooleanArray(1000).toTypedArray() })

        override fun processInstruction(instruction: Instruction, value: Boolean) = instruction.process(value)
    }

    private class BrightnessGrid : Grid<Int>() {
        override val grid = Array(1000, { IntArray(1000).toTypedArray() })

        override fun processInstruction(instruction: Instruction, value: Int) = instruction.process(value)
    }

    private data class Point(val x: Int, val y: Int)

    private data class InstructionWithPoints(val instruction: Instruction, val pointOne: Point, val pointTwo: Point)

    private fun String.toInstruction(): InstructionWithPoints {
        val instruction = when {
            this.startsWith("turn off") -> Instruction.OFF
            this.startsWith("turn on") -> Instruction.ON
            this.startsWith("toggle") -> Instruction.TOGGLE
            else -> throw IllegalStateException("Invalid instruction")
        }
        val halfMarker = this.indexOf(' ') + this.indexOf(',')
        val pointOne = this.substring(0, halfMarker).replace(Regex("[^\\d,]"), "").split(",")
        val secondHalf = this.substring(halfMarker + 1, this.length)
        val pointTwo = secondHalf.substring(secondHalf.indexOf(' ') + 1, secondHalf.length).split(",")
        return InstructionWithPoints(
            instruction,
            Point(pointOne[0].toInt(), pointOne[1].toInt()),
            Point(pointTwo[0].toInt(), pointTwo[1].toInt())
        )
    }

    private enum class Instruction {
        OFF {
            override fun process(boolean: Boolean): Boolean {
                return false
            }

            override fun process(int: Int): Int {
                return Math.max(0, int - 1)
            }
        },
        ON {
            override fun process(boolean: Boolean): Boolean {
                return true
            }

            override fun process(int: Int): Int {
                return int + 1
            }
        },
        TOGGLE {
            override fun process(boolean: Boolean): Boolean {
                return !boolean
            }

            override fun process(int: Int): Int {
                return int + 2
            }
        };

        abstract fun process(boolean: Boolean): Boolean
        abstract fun process(int: Int): Int
    }

    private fun produceFirstAnswer(): Int {
        val grid = OnOffGrid()
        this.splitData.map { it.toInstruction() }.forEach { grid.processInstruction(it) }
        return grid.grid.sumBy { it.count { boolean -> boolean } }
    }

    // Second part

    private fun produceSecondAnswer(): Int {
        val grid = BrightnessGrid()
        this.splitData.map { it.toInstruction() }.forEach { grid.processInstruction(it) }
        return grid.grid.sumBy { it.sum() }
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()

}
