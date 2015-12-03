package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 1)
open class Day1 : Solution {

    private fun produceFirstAnswer(): Int {
        // Get the number of increments
        val increments = this.getData().replace(")", "").length
        // Get the number of decrements
        val decrements = this.getData().replace("(", "").length
        // Subtracting the decrements from the increments will indicate the floor Santa has to go to.
        return increments - decrements
    }

    private fun produceSecondAnswer(): Int {
        // Start our count at 0
        var count = 0
        // Loop through every instruction in the data with its index
        for (c in this.getData().withIndex()) {
            // If it's an increment instruction, increment the count
            if (c.value == '(') count++
            // If it's a decrement instruction, decrement the count
            else if (c.value == ')') count--
            // If the count is -1 (the basement), return the current index plus one (starts at one)
            if (count == -1) {
                return c.index + 1
            }
        }
        // If the count never equals -1, the basement was never entered, indicating invalid data
        return -1
    }

    override fun produceAnswers(): Pair<Int, Int> {
        return this.produceFirstAnswer() to this.produceSecondAnswer()
    }
}
