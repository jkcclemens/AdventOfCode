package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 10)
open class Day10 : Solution {

    private fun lookAndSay(input: String): String {
        // NOTE: RIP non-StringBuilder users

        // Define the index of the first string of numbers
        var lastIndex = 0
        // Make a result StringBuilder
        var result = StringBuilder()
        // Define the last character we looked at
        var last: Char = input[0]
        // Iterate through each character with its index
        for (item in input.withIndex()) {
            // If we're at the first character, ignore it
            if (item.index == 0) continue
            // If this character isn't the same as the last character
            if (item.value != last) {
                // Add how many times we saw the previous character and then add the previous character, as per the
                // rules of look and say
                result.append("${item.index - lastIndex}$last")
                // Set the last index to this index
                lastIndex = item.index
            }
            // The last character we looked at is now this one
            last = item.value
        }
        // Do the same as we did in the loop for the last remnant of the original string
        val lastDistance = input.length - lastIndex
        if (lastDistance != 0) {
            result.append("$lastDistance${input[input.length - 1]}")
        }
        // Return the result
        return result.toString()
    }

    private fun produceFirstAnswer(): Int {
        var last = this.splitData[0]
        // Look and say the input 40 times
        for (i in 1..40) {
            last = this.lookAndSay(last)
        }
        // Return the resulting length
        return last.length
    }

    private fun produceSecondAnswer(): Int {
        var last = this.splitData[0]
        // Look and say the input 50 times
        for (i in 1..50) {
            last = this.lookAndSay(last)
        }
        // Return the resulting length
        return last.length
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}

