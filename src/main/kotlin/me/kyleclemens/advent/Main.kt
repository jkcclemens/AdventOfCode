package me.kyleclemens.advent

import me.kyleclemens.advent.days.Day1
import me.kyleclemens.advent.days.Day10
import me.kyleclemens.advent.days.Day11
import me.kyleclemens.advent.days.Day12
import me.kyleclemens.advent.days.Day13
import me.kyleclemens.advent.days.Day14
import me.kyleclemens.advent.days.Day15
import me.kyleclemens.advent.days.Day2
import me.kyleclemens.advent.days.Day3
import me.kyleclemens.advent.days.Day4
import me.kyleclemens.advent.days.Day5
import me.kyleclemens.advent.days.Day6
import me.kyleclemens.advent.days.Day7
import me.kyleclemens.advent.days.Day8
import me.kyleclemens.advent.days.Day9
import me.kyleclemens.advent.helpers.Solution

private fun timeDay(day: Int, solution: Solution) {
    val start = System.currentTimeMillis()
    val answers = solution.answers
    val diff = System.currentTimeMillis() - start
    println("Day $day: $answers – ${diff}ms")
}

fun main(args: Array<String>) {
    timeDay(1, Day1())
    timeDay(2, Day2())
    timeDay(3, Day3())
    timeDay(4, Day4()) // takes a while
    timeDay(5, Day5())
    timeDay(6, Day6())
    timeDay(7, Day7())
    timeDay(8, Day8())
    timeDay(9, Day9())
    timeDay(10, Day10())
    timeDay(11, Day11())
    timeDay(12, Day12())
    timeDay(13, Day13())
    timeDay(14, Day14())
    timeDay(15, Day15())
}
