package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import kotlin.text.Regex

@UsesData(day = 6)
open class Day6 : Solution {

    private class Grid {
        val grid = Array(1000, { BooleanArray(1000) })

        fun processInstruction(instructionWithPoints: InstructionWithPoints) {
            val p1 = instructionWithPoints.pointOne
            val p2 = instructionWithPoints.pointTwo
            for (x in p1.x..p2.x) {
                for (y in p1.y..p2.y) {
                    this.grid[x][y] = instructionWithPoints.instruction.process(this.grid[x][y])
                }
            }
        }

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
        },
        ON {
            override fun process(boolean: Boolean): Boolean {
                return true
            }
        },
        TOGGLE {
            override fun process(boolean: Boolean): Boolean {
                return !boolean
            }
        };

        abstract fun process(boolean: Boolean): Boolean
    }

    private fun produceFirstAnswer(): Int {
        val grid = Grid()
        this.data.split("\n").filterNot { it.isEmpty() }.map { it.toInstruction() }.forEach { grid.processInstruction(it) }
        return grid.grid.sumBy { it.count { boolean -> boolean } }
    }

    // Second part

    private class GridWithBrightness {
        val grid = Array(1000, { IntArray(1000) })

        fun processInstruction(instructionWithPoints: BrightnessInstructionWithPoints) {
            val p1 = instructionWithPoints.pointOne
            val p2 = instructionWithPoints.pointTwo
            for (x in p1.x..p2.x) {
                for (y in p1.y..p2.y) {
                    this.grid[x][y] = instructionWithPoints.instruction.process(this.grid[x][y])
                }
            }
        }
    }

    private data class BrightnessInstructionWithPoints(val instruction: BrightnessInstruction, val pointOne: Point, val pointTwo: Point)

    private fun String.toBrightnessInstruction(): BrightnessInstructionWithPoints {
        val instruction = when {
            this.startsWith("turn off") -> BrightnessInstruction.OFF
            this.startsWith("turn on") -> BrightnessInstruction.ON
            this.startsWith("toggle") -> BrightnessInstruction.TOGGLE
            else -> throw IllegalStateException("Invalid instruction")
        }
        val halfMarker = this.indexOf(' ') + this.indexOf(',')
        val pointOne = this.substring(0, halfMarker).replace(Regex("[^\\d,]"), "").split(",")
        val secondHalf = this.substring(halfMarker + 1, this.length)
        val pointTwo = secondHalf.substring(secondHalf.indexOf(' ') + 1, secondHalf.length).split(",")
        return BrightnessInstructionWithPoints(
            instruction,
            Point(pointOne[0].toInt(), pointOne[1].toInt()),
            Point(pointTwo[0].toInt(), pointTwo[1].toInt())
        )
    }

    private enum class BrightnessInstruction {
        OFF {
            override fun process(int: Int): Int {
                return Math.max(0, int - 1)
            }
        },
        ON {
            override fun process(int: Int): Int {
                return int + 1
            }
        },
        TOGGLE {
            override fun process(int: Int): Int {
                return int + 2
            }
        };

        abstract fun process(int: Int): Int
    }

    private fun produceSecondAnswer(): Int {
        val grid = GridWithBrightness()
        this.data.split("\n").filterNot { it.isEmpty() }.map { it.toBrightnessInstruction() }.forEach { grid.processInstruction(it) }
        return grid.grid.sumBy { it.sum() }
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()

}
