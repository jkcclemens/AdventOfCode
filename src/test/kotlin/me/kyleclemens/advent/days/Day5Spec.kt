package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day5Spec : MavenSpek() {
    override fun test() {
        given("a Day 5 solution") {
            val solution = Day5()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(236, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(51, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(1, "ugknbfddgicrmopn".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(1, "aaa".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "jchzalrnumimnmhp".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "haegwjzuvuyypxyu".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "dvszwmarrgswjxmb".answers.first)
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(1, "qjhvhtzxzqqjkmpb".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(1, "xxyxx".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "uurcxstgmygtbstg".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "ieodomkazucvgmuy".answers.second)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Int>
        get() = CustomDay5(this).answers

    private class CustomDay5(val customData: String) : Day5() {
        override val data = this.customData
    }
}
