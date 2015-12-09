package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day9Spec : MavenSpek() {
    override fun test() {
        given("a Day 9 solution") {
            val solution = Day9()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(141, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(736, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(605, "London to Dublin = 464\nLondon to Belfast = 518\nDublin to Belfast = 141".answers.first)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Int>
        get() = CustomDay9(this).answers

    private class CustomDay9(val customData: String) : Day9() {
        override val data = this.customData
    }

}
