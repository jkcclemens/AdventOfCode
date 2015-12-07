package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import kotlin.text.Regex

@UsesData(day = 7)
open class Day7 : Solution {

    private class BitwiseFun {
        val operations = arrayListOf<Operation>()
        val wires = hashMapOf<String, Int>()

        fun process() {
            val iterator = this.operations.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().function(this.wires)) {
                    iterator.remove()
                }
            }
        }

        fun addOperation(operation: Operation) {
            this.operations += operation
        }

        fun processUntilEmpty() {
            while (this.operations.isNotEmpty()) {
                this.process()
            }
        }
    }

    private class Operation(val function: (MutableMap<String, Int>) -> Boolean)

    private fun String.isInt(): Boolean {
        return try {
            this.toInt()
            true
        } catch(ex: NumberFormatException) {
            false
        }
    }

    private fun gate(left: String, right: String, wires: MutableMap<String, Int>, regex: Regex, function: (List<Int>) -> Int): Boolean? {
        val match = regex.matchEntire(left) ?: return null
        val groups = match.groups.map { it?.value }.let { it.takeLast(it.size - 1) }
        if (groups.any { it == null }) return null
        if (groups.filterNot { it!!.isInt() }.any { it!! !in wires }) return false
        wires[right] = function(groups.map { if (it!!.isInt()) it.toInt() else wires[it]!! })
        return true
    }

    private fun String.toOperation(): Operation {
        val (left, right) = this.split(" -> ")
        return Operation { wires ->
            if (left.isInt() && right !in wires) {
                wires[right] = left.toInt()
                return@Operation true
            }
            gate(left, right, wires, Regex("(\\w+) AND (\\w+)")) { comps -> comps[0] and comps[1] }?.let { return@Operation it }
            gate(left, right, wires, Regex("(\\w+) OR (\\w+)")) { comps -> comps[0] or comps[1] }?.let { return@Operation it }
            gate(left, right, wires, Regex("(\\w+) LSHIFT (\\w+)")) { comps -> comps[0] shl comps[1] }?.let { return@Operation it }
            gate(left, right, wires, Regex("(\\w+) RSHIFT (\\w+)")) { comps -> comps[0] shr comps[1] }?.let { return@Operation it }
            gate(left, right, wires, Regex("NOT (\\w+)")) { comps -> comps[0].inv() and 0xFFFF }?.let { return@Operation it }
            if (left !in wires) {
                return@Operation right in wires
            }
            wires[right] = wires[left]!!
            return@Operation true
        }
    }

    private fun produceFirstAnswer(): Int {
        val bitwiseFun = BitwiseFun()
        this.data.split("\n").filterNot { it.isEmpty() }.map { it.toOperation() }.forEach { bitwiseFun.addOperation(it) }
        bitwiseFun.processUntilEmpty()
        return bitwiseFun.wires["a"]!!
    }

    private fun produceSecondAnswer(): Int {
        val bitwiseFun = BitwiseFun()
        ("${this.produceFirstAnswer()} -> b\n" + this.data).split("\n").filterNot { it.isEmpty() }.map { it.toOperation() }.forEach { bitwiseFun.addOperation(it) }
        bitwiseFun.processUntilEmpty()
        return bitwiseFun.wires["a"]!!
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
