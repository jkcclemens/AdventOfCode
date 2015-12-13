package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 11)
open class Day11 : Solution {

    private class PasswordIncrementer(val base: String) {

        /**
         * Increments this string, so `"a"` becomes `"b"`, `"ab"` becomes `"ac"`, and `"az"` becomes `"ba"`.
         */
        private fun String.increment(): String {
            // Convert this string to a char array
            val chars = this.toCharArray()
            // Subtract 95, since 96 would put us at the ordinal position, so add one to get the next letter (-96 + 1)
            val next = chars.last().toInt() - 95
            // If we're over 26, we need to loop back around and increment the letter behind this one
            if (next > 26) {
                // If we're out of characters to increment, we have a bad string
                if (chars.size < 2) {
                    throw IllegalArgumentException("No letter to increment")
                }
                // Chop off the last char, make it a string, increment it, turn it back into an array, append 'a', then
                // convert it to a string and return it
                return (chars.take(chars.size - 1).joinToString("").increment().toCharArray() + 'a').joinToString("")
            } else {
                // Otherwise, just increment this char
                chars[chars.size - 1] = (next + 96).toChar()
            }
            // Return the array as a string
            return chars.joinToString("")
        }

        /**
         * Checks if this string has a "straight" of three letters. A straight is a group of letters that all come next
         * in the alphabet, so `"abc"` and `"xyz"` are both straights, but `"ate"` is not.
         */
        private fun String.hasStraight(): Boolean {
            // If there aren't enough letters to form a straight, then there's no straight
            if (this.length < 3) return false
            // Loop through each char with its index
            for (item in this.withIndex()) {
                // If there aren't enough letters to form a straight from this position, then there's no straight
                if (item.index + 2 > this.length - 1) return false
                // Get the current char as its int value
                val start = item.value.toInt()
                // Make sure the next two letters increase
                if (this[item.index + 1].toInt() == start + 1 && this[item.index + 2].toInt() == start + 2) {
                    // If they do, return true, since there's at least one straight
                    return true
                }
            }
            // Otherwise, there are no straights, so return false
            return false
        }

        /**
         * Checks if this string has any "bad" letters, which are `i`, `o`, and `l`.
         */
        private fun String.hasBadLetters(): Boolean {
            return this.contains("i") || this.contains("o") || this.contains("l")
        }

        /**
         * Checks if this string has two pairs of repeating letters, like `"aa"` and `"bb"`.
         */
        private fun String.hasTwoPairs(): Boolean {
            // Make a new set of characters that had pairs
            val matched = hashSetOf<Char>()
            for (c in this.withIndex()) {
                // If there aren't enough letters, break
                if (c.index == this.length - 1) break
                // If there's a pair, add the pair letter to the set
                if (c.value == this[c.index + 1]) matched.add(c.value)
            }
            // Return true if more than one pair of different letters was matched
            return matched.size > 1
        }

        /**
         * Checks if this string meets the requirements for a new password.
         */
        private fun String.meetsRequirements(): Boolean {
            return !this.hasBadLetters() && this.hasTwoPairs() && this.hasStraight()
        }

        /**
         * The next password that meets the requirements for a new password, starting from [base].
         */
        val nextPassword: String
            get() {
                // Assign base to pass
                var pass = base
                do {
                    // Increment pass until it meets the requirements
                    pass = pass.increment()
                } while (!pass.meetsRequirements())
                // Return the password that meets the requirements
                return pass
            }

    }

    private fun produceFirstAnswer(): String {
        // Take the base password and increment it until it meets the requirements
        return PasswordIncrementer(this.splitData[0]).nextPassword
    }

    private fun produceSecondAnswer(): String {
        // Take the base password and increment it until it meets the requirements, then increment that password until
        // a new password is found
        return PasswordIncrementer(PasswordIncrementer(this.splitData[0]).nextPassword).nextPassword
    }

    override val answers: Pair<String, String>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
