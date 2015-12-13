package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@UsesData(day = 12)
open class Day12 : Solution {

    private fun produceFirstAnswer(): Int {
        val data = this.splitData[0]
        var skip = 0
        return data.mapIndexed { i, c ->
            if (skip > 0) {
                skip--
                return@mapIndexed null
            }
            if (!c.isDigit()) return@mapIndexed null
            var num = "$c"
            var j = i
            while (data[++j].isDigit()) {
                num += data[j]
            }
            val finalNumber = num.toInt()
            skip = j - i
            return@mapIndexed if (i != 0 && data[i - 1] == '-') finalNumber * -1 else finalNumber
        }.filterNotNull().sum()
    }

    private fun JSONArray.sum(ignoreRed: Boolean = true): Int {
        return (0..this.length())
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
            .sum()
    }

    private fun JSONObject.sum(ignoreRed: Boolean = true): Int {
        if (ignoreRed && "red" in this.keys().asSequence()) return 0
        if (ignoreRed && this.keys().asSequence().map { this.optString(it, null) }.filterNotNull().any { it == "red" }) return 0
        return this.keys().asSequence()
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
            .sum()
    }

    private fun produceSecondAnswer(): Int {
        val data = this.splitData[0]
        return when (data[0]) {
            '{' ->
                JSONObject(data).sum(ignoreRed = true)
            '[' ->
                JSONArray(data).sum(ignoreRed = true)
            else ->
                throw IllegalArgumentException("Bad data (not JSON)")
        }
    }

    override val answers: Pair<Int, Any>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
