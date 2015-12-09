package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day6Spec : MavenSpek() {
    override fun test() {
        given("a Day 6 solution") {
            val solution = Day6()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(400410, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(15343601, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(1000000, "turn on 0,0 through 999,999".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(1000, "toggle 0,0 through 999,0".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "turn off 499,499 through 500,500".answers.first)
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(1, "turn on 0,0 through 0,0".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(2000000, "toggle 0,0 through 999,999".answers.second)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Int>
        get() = CustomDay6(this).answers

    private class CustomDay6(val customData: String) : Day6() {
        override val data = this.customData
    }

}
