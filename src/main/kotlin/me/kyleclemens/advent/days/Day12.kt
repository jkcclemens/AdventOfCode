package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@UsesData(day = 12)
open class Day12 : Solution {

    private fun produceFirstAnswer(): Int {
        // Don't process JSON! Just find all the numbers. AoC specifically tells us that "You will not encounter any
        // strings containing numbers."

        // Get the line of data
        val data = this.splitData[0]
        // Set a skip variable to 0
        var skip = 0
        return data
            // Map each character with its index
            .mapIndexed { i, c ->
                // If skip is greater than zero, return null and subtract one from skip
                if (skip > 0) {
                    skip--
                    return@mapIndexed null
                }
                // If this character isn't a digit, return null
                if (!c.isDigit()) return@mapIndexed null
                // Start a string of numbers
                var num = "$c"
                // Loop through the numbers in front of this one until out of digits
                var j = i
                while (data[++j].isDigit()) {
                    // Append the digit to the string
                    num += data[j]
                }
                // Convert the string to the number it represents
                val finalNumber = num.toInt()
                // Skip the characters that made up the number
                skip = j - i
                // Check if this number is negative and return it
                return@mapIndexed if (i != 0 && data[i - 1] == '-') finalNumber * -1 else finalNumber
            }
            // Filter out the nulls
            .filterNotNull()
            // Sum the numbers
            .sum()
    }

    private fun JSONArray.sum(ignoreRed: Boolean = true): Int {
        // Note that AoC tells us not to ignore arrays with "red" in them, but we need to pass the ignoreRed param to
        // objects that are being summed

        return (0..this.length())
            // Map each index into its int value, mapping objects and arrays to their sums
            .map { i ->
                this.optJSONObject(i)?.let {
                    return@map it.sum(ignoreRed)
                }
                this.optJSONArray(i)?.let {
                    return@map it.sum(ignoreRed)
                }
                return@map try {
                    this.getInt(i)
                } catch(ex: JSONException) {
                    0
                }
            }
            // Sum the result
            .sum()
    }

    private fun JSONObject.sum(ignoreRed: Boolean = true): Int {
        // If we're ignoring objects with "red" in them, check the keys
        if (ignoreRed && "red" in this.keys().asSequence()) return 0
        // Check the values
        if (ignoreRed && this.keys().asSequence().map { this.optString(it, null) }.filterNotNull().any { it == "red" }) return 0
        return this.keys().asSequence()
            // Map each key to its int value, mapping objects and arrays to their sums
            .map { key ->
                this.optJSONObject(key)?.let {
                    return@map it.sum(ignoreRed)
                }
                this.optJSONArray(key)?.let {
                    return@map it.sum(ignoreRed)
                }
                return@map try {
                    this.getInt(key)
                } catch(ex: JSONException) {
                    0
                }
            }
            // Sum the result
            .sum()
    }

    private fun produceSecondAnswer(): Int {
        // While I'm sure there could be a way to do this without processing the JSON into an object, the idea of doing
        // something like that hurts my brain.

        // Get the line of data
        val data = this.splitData[0]
        return when (data[0]) {
        // If the first character is {, it's an object, so sum it with the object function
            '{' ->
                JSONObject(data).sum(ignoreRed = true)
        // If the first character is [, it's an array, so sum it with the array function
            '[' ->
                JSONArray(data).sum(ignoreRed = true)
        // If the first character is anything else, the input is not JSON
            else ->
                throw IllegalArgumentException("Bad data (not JSON)")
        }
    }

    override val answers: Pair<Int, Any>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
