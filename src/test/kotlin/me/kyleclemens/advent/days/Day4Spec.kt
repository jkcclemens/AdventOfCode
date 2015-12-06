package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day4Spec : MavenSpek() {
    override fun test() {
        given("a Day 4 solution") {
            val solution = Day4()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(117946, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(3938038, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(609043, "abcdef".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(1048970, "pqrstuv".answers.first)
                }
            }
            // NOTE: There are no examples for part 2
        }
    }

    private val String.answers: Pair<Int, Any>
        get() = CustomDay4(this).answers

    private class CustomDay4(val customData: String) : Day4() {
        override val data = this.customData
    }

}
