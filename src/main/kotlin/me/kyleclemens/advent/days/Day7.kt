package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import kotlin.text.Regex

@UsesData(day = 7)
open class Day7 : Solution {

    private class BitwiseFun {
        /**
         * Mutable list of operations to apply
         */
        val operations = arrayListOf<Operation>()
        /**
         * Map of wires, names and values.
         */
        val wires = hashMapOf<String, Int>()

        /**
         * Processes all operations in [operations] once. If `true` is returned by an operation, it is removed from the
         * list. If false is returned, it is kept in the list to be run again.
         */
        fun process() {
            val iterator = this.operations.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().function(this.wires)) {
                    iterator.remove()
                }
            }
        }

        /**
         * Adds an operation to [operations].
         */
        fun addOperation(operation: Operation) {
            this.operations += operation
        }

        /**
         * Runs [process] until [operations] is empty.
         */
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

    /**
     * Processes a gate.
     * @param[left] The left side of the instruction
     * @param[right] The right side of the instruction
     * @param[wires] The wires, names and values
     * @param[regex] The regular expression that [left] must match.
     * @param[function] The function to invoke with a list of wire values matched by the regular expression (groups).
     *                  This should return the value of the wire specified by [right]. This is only invoked if all
     *                  groups match and every wire mentioned is in [wires].
     * @return Returns null if the regex does not match or if all groups do not match. Returns false if not all wires
     *         are in [wires]. Otherwise, returns true after setting [right] in [wires] to the value returned by
     *         [function].
     */
    private fun gate(left: String, right: String, wires: MutableMap<String, Int>, regex: Regex, function: (List<Int>) -> Int): Boolean? {
        val match = regex.matchEntire(left) ?: return null
        val groups = match.groups.map { it?.value }.let { it.takeLast(it.size - 1) }
        if (groups.any { it == null }) return null
        if (groups.filterNot { it!!.isInt() }.any { it!! !in wires }) return false
        wires[right] = function(groups.map { if (it!!.isInt()) it.toInt() else wires[it]!! })
        return true
    }

    /**
     * Converts this string to an [Operation].
     */
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
                return@Operation false
            }
            wires[right] = wires[left]!!
            return@Operation true
        }
    }

    private fun produceFirstAnswer(): Int {
        val bitwiseFun = BitwiseFun()
        this.splitData.map { it.toOperation() }.forEach { bitwiseFun.addOperation(it) }
        bitwiseFun.processUntilEmpty()
        return bitwiseFun.wires["a"]!!
    }

    private fun produceSecondAnswer(): Int {
        val bitwiseFun = BitwiseFun()
        this.data.replace(Regex("\\d+ -> b\n"), "${this.produceFirstAnswer()} -> b\n").split("\n").filterNot { it.isEmpty() }.map { it.toOperation() }.forEach { bitwiseFun.addOperation(it) }
        bitwiseFun.processUntilEmpty()
        return bitwiseFun.wires["a"]!!
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
