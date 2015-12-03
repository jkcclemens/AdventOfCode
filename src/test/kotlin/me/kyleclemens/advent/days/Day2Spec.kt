package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day2Spec : MavenSpek() {
    override fun test() {
        given("a Day 2 solution") {
            val solution = Day2()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(1588178, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(3783758, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(58, "2x3x4".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(43, "1x1x10".answers.first)
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(34, "2x3x4".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(14, "1x1x10".answers.second)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Int>
        get() = CustomDay2(this).answers

    private class CustomDay2(val customData: String) : Day2() {
        override fun getData(): String {
            return this.customData
        }
    }

}
