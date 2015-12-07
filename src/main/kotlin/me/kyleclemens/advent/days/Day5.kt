package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 5)
open class Day5 : Solution {

    /**
     * Checks if this string has repeating letters like "aa" or "bb"
     */
    private fun String.hasRepeats(): Boolean {
        for (c in this.withIndex()) {
            // If we've reached the end of and odd-length string, return false
            if (c.index == this.length - 1) return false
            // If this letter is the same as the next letter, there's a repeat, so return true
            if (c.value == this[c.index + 1]) return true
        }
        // No repeats were found
        return false
    }

    /**
     * Checks if this string has repeating letters with a letter between them, like "aba" or "bcb"
     */
    private fun String.hasRepeatsWithInBetweener(): Boolean {
        for (c in this.withIndex()) {
            // If there's not enough letters to check, return false
            if (c.index == this.length - 2) return false
            // Check the letter after the next letter. If it's the same as this letter, return true
            if (c.value == this[c.index + 2]) return true
        }
        // No repeats found
        return false
    }

    /**
     * Checks if this string has at least two pairs of repeating letters like "xy" in "xyaaxy"
     */
    private fun String.hasPairs(): Boolean {
        for (c in this.withIndex()) {
            // If there aren't enough letters, return false
            if (c.index == this.length - 2) return false
            // Get the current pair
            val pair = this.substring(c.index, c.index + 2)
            // If replacing this pair with nothing reduces the length by more than 2, we've replaced more than one
            // instance of the pair, so return true
            if (this.length - (this.replace(pair, "").length) > 2) return true
        }
        // Return false
        return false
    }

    /**
     * Checks if this string is nice using the first method described in Advent of Code.
     */
    private fun String.isNiceFirstMethod(): Boolean {
        // Make sure the string doesn't contain any of the naughty pairs of letters
        if (this.contains("ab") || this.contains("cd") || this.contains("pq") || this.contains("xy")) return false
        // If the string doesn't have repeats, it's naughty
        if (!this.hasRepeats()) return false
        // If the string has less than three vowels, it's naughty
        if (this.count { it in "aeiou" } < 3) return false
        // Otherwise, it's nice!
        return true
    }

    /**
     * Checks if this string is nice using the second method described in Advent of Code.
     */
    private fun String.isNiceSecondMethod(): Boolean {
        // If the string doesn't have at least two repeats with a letter between them, it's naughty
        if (!this.hasRepeatsWithInBetweener()) return false
        // If the string doesn't have any pairs, it's naughty
        if (!this.hasPairs()) return false
        // Otherwise, it's nice
        return true
    }

    private fun produceFirstAnswer(): Int {
        return this.splitData.count { it.isNiceFirstMethod() }
    }

    private fun produceSecondAnswer(): Int {
        return this.splitData.count { it.isNiceSecondMethod() }
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
