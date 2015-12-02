package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.DataCarrying
import me.kyleclemens.advent.helpers.ProducesAnswers
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 1)
class Day1 : DataCarrying, ProducesAnswers {

    private fun produceFirstAnswer(): String {
        val increments = this.getData().replace(")", "").length
        val decrements = this.getData().replace("(", "").length
        return (increments - decrements).toString()
    }

    private fun produceSecondAnswer(): String {
        var count = 0
        for (c in this.getData().withIndex()) {
            if (c.value == '(') count++
            else if (c.value == ')') count--
            if (count == -1) {
                return c.index.toString()
            }
        }
        throw IllegalStateException("The basement is never entered")
    }

    override fun produceAnswers(): Pair<String, String> {
        return this.produceFirstAnswer() to this.produceSecondAnswer()
    }
}
