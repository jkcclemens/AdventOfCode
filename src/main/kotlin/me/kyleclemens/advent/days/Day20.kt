package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 20)
open class Day20 : Solution {

    private val Int.factors: List<Int>
        get() {
            val factors = arrayListOf<Int>()
            val max = Math.sqrt(this.toDouble()).toInt()
            for (i in 1..max) {
                if (this % i == 0) {
                    factors.add(i)
                    if (i != this / i) {
                        factors.add(this / i)
                    }
                }
            }
            return factors
        }

    private fun produceFirstAnswer(): Int {
        var house = 0
        while (++house > 0) {
            if (house.factors.map { it * 10 }.sum() >= this.splitData[0].toInt()) return house
        }
        throw IllegalStateException("Should never happen")
    }

    private fun produceSecondAnswer(): Int {
        val elves = hashMapOf<Int, Int>()
        var house = 0
        while (++house > 0) {
            val delivered = house.factors
                .map {
                    if (it in elves && elves[it]!! >= 50) return@map 0
                    elves[it] = if (it in elves) elves[it]!! + 1 else 1
                    it * 11
                }
                .sum()
            if (delivered >= this.splitData[0].toInt()) return house
        }
        throw IllegalStateException("Should never happen")
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
