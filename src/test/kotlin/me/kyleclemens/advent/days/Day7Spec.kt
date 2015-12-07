package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day7Spec : MavenSpek() {
    override fun test() {
        given("a Day 7 solution") {
            val solution = Day7()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(3176, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(14710, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(65079, """123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> a""".answers.first)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Any>
        get() = CustomDay7(this).answers

    private class CustomDay7(val customData: String) : Day7() {
        override val data = this.customData
    }

}
