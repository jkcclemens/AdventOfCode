package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 14)
open class Day14 : Solution {

    private class Reindeer(val flyTime: Int, val restTime: Int, val speed: Int) {
        /**
         * The current state of this reindeer. All reindeer start as [ReindeerState.INACTIVE].
         */
        var state: ReindeerState = ReindeerState.INACTIVE
        /**
         * The seconds this reindeer has remaining in its current state.
         */
        var secondsRemaining: Int = 0
        /**
         * The distance this reindeer has traveled.
         */
        var distance: Int = 0
        /**
         * This reindeer's current score.
         */
        var score: Int = 0

        /**
         * Moves this reindeer to its next state.
         */
        private fun nextState() {
            this.state = when (this.state) {
            // When the reindeer is inactive or resting, the next state is flying
            // NOTE: I read somewhere that Kotlin is changing this syntax to replace the comma with two pipes.
                ReindeerState.INACTIVE, ReindeerState.RESTING -> {
                    // Set the seconds for this state
                    this.secondsRemaining = this.flyTime
                    ReindeerState.FLYING
                }
            // When the reindeer is flying, the next state is resting
                ReindeerState.FLYING -> {
                    // Set the seconds for this state
                    this.secondsRemaining = this.restTime
                    ReindeerState.RESTING
                }
            }
        }

        /**
         * Advances this reindeer one second forward, decrementing [secondsRemaining] and incrementing [distance] if
         * necessary. This will also change this reindeer's state if necessary.
         */
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
            /**
             * The reindeer has not started racing yet.
             */
            INACTIVE,
            /**
             * The reindeer is resting.
             */
            RESTING,
            /**
             * The reindeer is flying.
             */
            FLYING
        }
    }

    /**
     * A set of inactive reindeer generated from [splitData]. Each call to this generates a new set.
     */
    private val inactiveReindeer: Set<Reindeer>
        get() = this.splitData
            .map {
                // Split the line of data by spaces
                val split = it.split(" ")
                // The fourth word is the speed in km/h
                val speed = split[3].toInt()
                // The seventh word is how long the reindeer can fly for in seconds
                val flyTime = split[6].toInt()
                // The fourteenth word is how long the reindeer must rest after flying, in seconds
                val restTime = split[13].toInt()
                // Make a new reindeer with these attributes (this used to have a name parameter as well, but AoC never
                // asked for it.
                Reindeer(flyTime, restTime, speed)
            }
            // Convert the list to a set
            .toSet()

    fun produceFirstAnswer(seconds: Int = 2503): Int {
        // Get the reindeer
        val reindeer = this.inactiveReindeer
        // Advance all the reindeer one second for every second of the race
        for (i in 0..seconds) {
            reindeer.forEach { it.advanceOneSecond() }
        }
        // Map each reindeer to its total distance traveled and find the max distance
        return reindeer.map { it.distance }.max()!!
    }

    fun produceSecondAnswer(seconds: Int = 2503): Int {
        // Get the reindeer
        val reindeer = this.inactiveReindeer
        // Advance all the reindeer one second for every second of the race
        for (i in 0..seconds) {
            reindeer.forEach { it.advanceOneSecond() }
            // Find the reindeer that has gone the farthest and increment its score
            reindeer.reduce { reindeer1, reindeer2 -> if (reindeer1.distance > reindeer2.distance) reindeer1 else reindeer2 }.score++
        }
        // Map each reindeer to its total score and find the max score
        return reindeer.map { it.score }.max()!!
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
