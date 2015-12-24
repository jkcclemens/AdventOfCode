package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 19)
open class Day19 : Solution {

    private val replacements: Map<String, Set<String>>
        get() {
            val map = hashMapOf<String, MutableSet<String>>()
            val assignments = this.data.split("\n\n")[0].split("\n")
            assignments.map { it.split(" => ") }.forEach {
                map[it[0]] = if (it[0] in map) (map[it[0]]!! + setOf(it[1])).toHashSet() else hashSetOf(it[1])
            }
            return map
        }

    private val base: String
        get() = this.data.split("\n\n")[1].trim()

    private fun produceFirstAnswer(): Int {
        val molecules = hashSetOf<String>()
        val base = this.base
        for ((k, v) in this.replacements) {
            for (replacement in v) {
                var lastIndex = -2
                while (lastIndex != -1) {
                    lastIndex = base.indexOf(k, if (lastIndex == -2) 0 else lastIndex + 1)
                    if (lastIndex == -1) continue
                    molecules.add(
                        base.substring(0, lastIndex) + base.substring(lastIndex, base.length).replaceFirst(k, replacement)
                    )
                }
            }
        }
        return molecules.size
    }

    private fun produceSecondAnswer(): Int {
        var molecule = this.base.reversed()
        val replacements = hashMapOf<String, String>()
        this.replacements.forEach { entry -> entry.value.forEach { replacements[it.reversed()] = entry.key.reversed() } }
        val regex = Regex(replacements.keys.joinToString("|"))
        var count = 0
        while (molecule != "e") {
            molecule = regex.find(molecule)!!.value.run { molecule.replaceFirst(this, replacements[this]!!) }
            count++
        }
        return count
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
