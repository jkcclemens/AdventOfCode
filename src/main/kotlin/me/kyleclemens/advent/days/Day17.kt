package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 17)
open class Day17 : Solution {

    fun produceFirstAnswer(target: Int = 150): Int {
        val ints = this.splitData.map { it.toInt() }
        var solutions = 0
        for (i in 0..(1 shl ints.size)) {
            var t = i
            var sum = 0
            for (int in ints) {
                if (t % 2 == 1) {
                    sum += int
                }
                t /= 2
            }
            if (sum == target) {
                solutions++
            }
        }
        return solutions
    }

    fun produceSecondAnswer(target: Int = 150): Int {
        val ints = this.splitData.map { it.toInt() }
        var solutions = arrayListOf<List<Int>>()
        for (i in 0..(1 shl ints.size)) {
            var t = i
            var sum = 0
            var solution = arrayListOf<Int>()
            for (int in ints) {
                if (t % 2 == 1) {
                    sum += int
                    solution.add(int)
                }
                t /= 2
            }
            if (sum == target) {
                solutions.add(solution)
            }
        }
        return solutions.filter { it.size == solutions.map { it.size }.min()!! }.size
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}

