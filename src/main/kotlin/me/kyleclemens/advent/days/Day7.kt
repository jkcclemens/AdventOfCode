package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

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
            // Iterate through all operations
            while (iterator.hasNext()) {
                // Call the operation's function
                if (iterator.next().function(this.wires)) {
                    // If it returns true, the operation was successful, so remove it from the list
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
        // Get the regex match or return null if there is no match
        val match = regex.matchEntire(left) ?: return null
        // Map each group to its value, then take all values but the first, since the first is the whole string
        val groups = match.groups.map { it?.value }.let { it.takeLast(it.size - 1) }
        // If any values are null, return null
        if (groups.any { it == null }) return null
        // If any non-int values aren't present in wires, return false, since we're not ready to run this operation
        if (groups.filterNot { it!!.isInt() }.any { it!! !in wires }) return false
        // Otherwise, assign the wire the value returned by function
        wires[right] = function(groups.map { if (it!!.isInt()) it.toInt() else wires[it]!! })
        // Return true, since the operation was successful
        return true
    }

    /**
     * Converts this string to an [Operation].
     */
    private fun String.toOperation(): Operation {
        // Split the string into a left and right side
        val (left, right) = this.split(" -> ")
        return Operation { wires ->
            // If the left side is an int and the right side isn't assigned
            if (left.isInt() && right !in wires) {
                // Assign the right sign
                wires[right] = left.toInt()
                // Return true, since the operation was successful
                return@Operation true
            }
            // Check for an AND gate and return if the operation was successful
            gate(left, right, wires, Regex("(\\w+) AND (\\w+)")) { comps -> comps[0] and comps[1] }?.let { return@Operation it }
            // Check for an OR gate and return if the operation was successful
            gate(left, right, wires, Regex("(\\w+) OR (\\w+)")) { comps -> comps[0] or comps[1] }?.let { return@Operation it }
            // Check for an LSHIFT gate and return if the operation was successful
            gate(left, right, wires, Regex("(\\w+) LSHIFT (\\w+)")) { comps -> comps[0] shl comps[1] }?.let { return@Operation it }
            // Check for an RSHIFT gate and return if the operation was successful
            gate(left, right, wires, Regex("(\\w+) RSHIFT (\\w+)")) { comps -> comps[0] shr comps[1] }?.let { return@Operation it }
            // Check for a NOT gate and return if the operation was successful
            gate(left, right, wires, Regex("NOT (\\w+)")) { comps -> comps[0].inv() and 0xFFFF }?.let { return@Operation it }
            // If the left side isn't a recognized gate and it isn't a wire that is set, return false
            if (left !in wires) {
                return@Operation false
            }
            // Otherwise, assign the right side to the value of the left side
            wires[right] = wires[left]!!
            // Return true, since the operation was successful
            return@Operation true
        }
    }

    private fun produceFirstAnswer(): Int {
        // Make a new funtime
        val bitwiseFun = BitwiseFun()
        // Convert the data to operations, and add each operation to the funtime
        this.splitData.map { it.toOperation() }.forEach { bitwiseFun.addOperation(it) }
        // Process the operations until they've all finished
        bitwiseFun.processUntilEmpty()
        // Return the value of the "a" wire
        return bitwiseFun.wires["a"]!!
    }

    private fun produceSecondAnswer(): Int {
        // Make a new funtime
        val bitwiseFun = BitwiseFun()
        // Replace the "b" wire assignment with the value return by the first answer, then convert the data to
        // operations, and add each operation to the funtime
        this.data.replace(Regex("\\d+ -> b\n"), "${this.produceFirstAnswer()} -> b\n").split("\n").filterNot { it.isEmpty() }.map { it.toOperation() }.forEach { bitwiseFun.addOperation(it) }
        // Process the operations until they've all finished
        bitwiseFun.processUntilEmpty()
        // Return the value of the "a" wire
        return bitwiseFun.wires["a"]!!
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
