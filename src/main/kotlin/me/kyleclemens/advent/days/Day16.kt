package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 16)
class Day16 : Solution {

    private class Sue(val map: Map<String, Int>)

    private val targetSue = Sue(mapOf(
        "number" to -1,
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    ))

    private val defaultMap = mapOf(
        "number" to -1,
        "children" to -1,
        "cats" to -1,
        "samoyeds" to -1,
        "pomeranians" to -1,
        "akitas" to -1,
        "vizslas" to -1,
        "goldfish" to -1,
        "trees" to -1,
        "cars" to -1,
        "perfumes" to -1
    )

    private fun String.toSue(): Sue {
        val (name, data) = this.split(": ", limit = 2)
        val sue = (this@Day16.defaultMap + data.split(", ").toMap({ it.split(": ")[0] }, { it.split(": ")[1].toInt() })).toLinkedMap()
        sue["number"] = name.split(" ")[1].toInt()
        return Sue(sue)
    }

    private val allSues: Set<Sue>
        get() = this.splitData.map { it.toSue() }.toSet()

    private fun produceFirstAnswer(): Int {
        val sue = this.allSues
            .map { it.map.filter { entry -> entry.value > -1 } }
            .filter { it.all { entry -> entry.key == "number" || this.targetSue.map[entry.key] == entry.value } }
            .single()
        return sue["number"]!!
    }

    private fun produceSecondAnswer(): Int {
        val sue = this.allSues
            .map { it.map.filter { entry -> entry.value > -1 } }
            .filter {
                it.all { entry ->
                    if (entry.value < 0) return@all true
                    when (entry.key) {
                        "number" -> true
                        "cats", "trees" -> this.targetSue.map[entry.key]!! < entry.value
                        "pomeranians", "goldfish" -> this.targetSue.map[entry.key]!! > entry.value
                        else -> this.targetSue.map[entry.key] == entry.value
                    }
                }
            }
            .single()
        return sue["number"]!!
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
