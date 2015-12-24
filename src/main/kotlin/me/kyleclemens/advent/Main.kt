package me.kyleclemens.advent

import me.kyleclemens.advent.days.Day22
import me.kyleclemens.advent.helpers.Solution

private fun timeDay(day: Int, solution: Solution) {
    val start = System.currentTimeMillis()
    val answers = solution.answers
    val diff = System.currentTimeMillis() - start
    println("Day $day: $answers – ${diff}ms")
}

fun main(args: Array<String>) {
    /*timeDay(1, Day1())
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
    timeDay(16, Day16())
    timeDay(17, Day17())
    timeDay(18, Day18())
    timeDay(19, Day19())
    timeDay(20, Day20())
    timeDay(21, Day21())*/
    timeDay(22, Day22())
}
