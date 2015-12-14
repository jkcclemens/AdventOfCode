package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 14)
open class Day14 : Solution {

    private class Reindeer(val flyTime: Int, val restTime: Int, val speed: Int) {
        var state: ReindeerState = ReindeerState.INACTIVE
        var secondsRemaining: Int = 0
        var distance: Int = 0
        var score: Int = 0

        private fun nextState() {
            this.state = when (this.state) {
                ReindeerState.INACTIVE, ReindeerState.RESTING -> {
                    this.secondsRemaining = this.flyTime
                    ReindeerState.FLYING
                }
                ReindeerState.FLYING -> {
                    this.secondsRemaining = this.restTime
                    ReindeerState.RESTING
                }
            }
        }

        fun advanceOneSecond() {
            if (this.secondsRemaining < 1) {
                this.nextState()
            }
            this.secondsRemaining--
            if (this.state == ReindeerState.FLYING) {
                this.distance += speed
            }
        }

        private enum class ReindeerState {
            INACTIVE,
            RESTING,
            FLYING
        }
    }

    private val inactiveReindeer: Set<Reindeer>
        get() = this.splitData
            .map {
                val split = it.split(" ")
                val speed = split[3].toInt()
                val flyTime = split[6].toInt()
                val restTime = split[13].toInt()
                Reindeer(flyTime, restTime, speed)
            }
            .toSet()

    fun produceFirstAnswer(seconds: Int = 2503): Int {
        val reindeer = this.inactiveReindeer
        for (i in 0..seconds) {
            reindeer.forEach { it.advanceOneSecond() }
        }
        return reindeer.map { it.distance }.max()!!
    }

    fun produceSecondAnswer(seconds: Int = 2503): Int {
        val reindeer = this.inactiveReindeer
        for (i in 0..seconds) {
            reindeer.forEach { it.advanceOneSecond() }
            reindeer.reduce { reindeer1, reindeer2 -> if (reindeer1.distance > reindeer2.distance) reindeer1 else reindeer2 }.score++
        }
        return reindeer.map { it.score }.max()!!
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
