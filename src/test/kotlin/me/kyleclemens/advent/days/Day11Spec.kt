package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day11Spec : MavenSpek() {
    override fun test() {
        given("a Day 11 solution") {
            val solution = Day11()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals("cqjxxyzz", answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals("cqkaabcc", answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals("abcdffaa", "abcdefgh".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals("ghjaabcc", "ghijklmn".answers.first)
                }
            }
        }
    }

    private val String.answers: Pair<String, String>
        get() = CustomDay11(this).answers

    private class CustomDay11(val customData: String) : Day11() {
        override val data = this.customData
    }
}
