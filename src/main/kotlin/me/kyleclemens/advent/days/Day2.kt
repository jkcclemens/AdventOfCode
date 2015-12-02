package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.DataCarrying
import me.kyleclemens.advent.helpers.ProducesAnswers
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 2)
class Day2 : DataCarrying, ProducesAnswers {

    private data class Dimensions(val length: Int, val width: Int, val height: Int)

    private fun String.toDimensions(): Dimensions {
        require(this.count { it == 'x' } == 2)
        return this.split('x').map { it.toInt() }.run { Dimensions(this[0], this[1], this[2]) }
    }

    private fun getDimensions(): List<Dimensions> {
        return this.getData().split("\n").filterNot { it.isEmpty() }.map { it.toDimensions() }
    }

    private fun produceFirstAnswer(): String {
        return this.getDimensions().map { dimension ->
            val side1 = 2 * dimension.length * dimension.width
            val side2 = 2 * dimension.width * dimension.height
            val side3 = 2 * dimension.height * dimension.length
            val smallest = listOf(side1, side2, side3).min()!! / 2
            return@map side1 + side2 + side3 + smallest
        }.sum().toString()
    }

    private fun produceSecondAnswer(): String {
        return this.getDimensions().map { dimension ->
            val volume = dimension.height * dimension.width * dimension.length
            val side1 = dimension.height * 2
            val side2 = dimension.length * 2
            val side3 = dimension.width * 2
            val smallest = listOf(side1 + side2, side1 + side3, side2 + side1, side2 + side3, side3 + side1, side3 + side2).min()!!
            return@map volume + smallest
        }.sum().toString()
    }

    override fun produceAnswers(): Pair<String, String> {
        return this.produceFirstAnswer() to this.produceSecondAnswer()
    }
}
