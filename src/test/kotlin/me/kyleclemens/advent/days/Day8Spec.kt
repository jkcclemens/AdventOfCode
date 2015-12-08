package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day8Spec : MavenSpek() {
    override fun test() {
        given("a Day 8 solution") {
            val solution = Day8()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(1333, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(2046, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(12, "\"\"\n\"abc\"\n\"aaa\\\"aaa\"\n\"\\x27\"".answers.first)
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(19, "\"\"\n\"abc\"\n\"aaa\\\"aaa\"\n\"\\x27\"".answers.second)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Any>
        get() = CustomDay8(this).answers

    private class CustomDay8(val customData: String) : Day8() {
        override val data = this.customData
    }

}
