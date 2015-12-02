package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.DataCarrying
import me.kyleclemens.advent.helpers.ProducesAnswers
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 1)
class Day1 : DataCarrying, ProducesAnswers {

    private fun produceFirstAnswer(): String {
        // Get the number of increments
        val increments = this.getData().replace(")", "").length
        // Get the number of decrements
        val decrements = this.getData().replace("(", "").length
        // Subtracting the decrements from the increments will indicate the floor Santa has to go to.
        return (increments - decrements).toString()
    }

    private fun produceSecondAnswer(): String {
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
                return (c.index + 1).toString()
            }
        }
        // If the count never equals -1, the basement was never entered, indicating invalid data
        throw IllegalStateException("The basement is never entered")
    }

    override fun produceAnswers(): Pair<String, String> {
        return this.produceFirstAnswer() to this.produceSecondAnswer()
    }
}
