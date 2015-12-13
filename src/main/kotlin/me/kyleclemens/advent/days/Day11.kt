package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 11)
open class Day11 : Solution {

    private class PasswordIncrementer(val base: String) {

        private fun String.increment(): String {
            val chars = this.toCharArray()
            // Subtract 95, since 96 would put us at the ordinal position, so add one to get the next letter (-96 + 1)
            val next = chars.last().toInt() - 95
            if (next > 26) {
                if (chars.size < 2) {
                    throw IllegalArgumentException("No letter to increment")
                }
                return (chars.take(chars.size - 1).joinToString("").increment().toCharArray() + 'a').joinToString("")
            } else {
                chars[chars.size - 1] = (next + 96).toChar()
            }
            return chars.joinToString("")
        }

        private fun String.hasStraight(): Boolean {
            if (this.length < 3) return false
            for (item in this.withIndex()) {
                if (item.index + 2 > this.length - 1) return false
                val start = item.value.toInt()
                if (this[item.index + 1].toInt() == start + 1 && this[item.index + 2].toInt() == start + 2) {
                    return true
                }
            }
            return false
        }

        private fun String.hasBadLetters(): Boolean {
            return this.contains("i") || this.contains("o") || this.contains("l")
        }

        private fun String.hasTwoPairs(): Boolean {
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

        private fun String.meetsRequirements(): Boolean {
            return !this.hasBadLetters() && this.hasTwoPairs() && this.hasStraight()
        }

        val nextPassword: String
            get() {
                var pass = base
                do {
                    pass = pass.increment()
                } while (!pass.meetsRequirements())
                return pass
            }

    }

    private fun produceFirstAnswer(): String {
        return PasswordIncrementer(this.splitData[0]).nextPassword
    }

    private fun produceSecondAnswer(): String {
        return PasswordIncrementer(PasswordIncrementer(this.splitData[0]).nextPassword).nextPassword
    }

    override val answers: Pair<String, String>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
