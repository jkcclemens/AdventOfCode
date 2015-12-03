package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day3Spec : MavenSpek() {
    override fun test() {
        given("a Day 3 solution") {
            val solution = Day3()
            on("producing the answers") {
                val answers = solution.produceAnswers()
                it("should have the correct first answer") {
                    assertEquals(2592, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(2360, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(2, ">".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(4, "^>v<".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(2, "^v^v^v^v^v".answers.first)
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(3, "^v".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(3, "^>v<".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(11, "^v^v^v^v^v".answers.second)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Int>
        get() = CustomDay3(this).produceAnswers()

    private class CustomDay3(val customData: String) : Day3() {
        override fun getData(): String {
            return this.customData
        }
    }

}
