package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import me.kyleclemens.advent.helpers.neighbors

@UsesData(day = 18)
class Day18 : Solution {

    private class LightGrid(val lights: Array<BooleanArray>) {

        private fun copy(): Array<BooleanArray> {
            val copy = Array(100) { BooleanArray(100) }
            for (row in copy.withIndex()) {
                copy[row.index] = this.lights[row.index].copyOf()
            }
            return copy
        }

        fun animate() {
            val state = this.copy()
            with(this.lights) {
                for (row in this.withIndex()) {
                    for (col in row.value.withIndex()) {
                        val light = state[row.index][col.index]
                        val neighbors = state.neighbors(row.index, col.index)
                        this[row.index][col.index] = if (light) {
                            neighbors.count { it.value } in setOf(2, 3)
                        } else {
                            neighbors.count { it.value } == 3
                        }
                    }
                }
            }
        }

        fun animate(steps: Int) {
            for (i in 1..steps) {
                this.animate()
            }
        }

    }

    private fun List<String>.toLightGrid(): LightGrid {
        val lights = Array(100) { BooleanArray(100) }
        for (row in this.withIndex()) {
            for (col in row.value.withIndex()) {
                lights[row.index][col.index] = col.value == '#'
            }
        }
        return LightGrid(lights)
    }

    private fun produceFirstAnswer(): Int {
        val grid = this.splitData.toLightGrid()
        grid.animate(100)
        return grid.lights.map { it.count { it } }.sum()
    }

    private fun cornersTrue(array: Array<BooleanArray>) {
        array[array.size - 1][array[array.size - 1].size - 1] = true
        array[array.size - 1][0] = true
        array[0][array[array.size - 1].size - 1] = true
        array[0][0] = true
    }

    private fun produceSecondAnswer(): Int {
        val grid = this.splitData.toLightGrid()
        this.cornersTrue(grid.lights)
        for (i in 1..100) {
            grid.animate()
            this.cornersTrue(grid.lights)
        }
        return grid.lights.map { it.count { it } }.sum()
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
