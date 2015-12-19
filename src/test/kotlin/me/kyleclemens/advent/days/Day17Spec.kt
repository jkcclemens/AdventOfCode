package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day17Spec : MavenSpek() {
    override fun test() {
        given("a Day 17 solution") {
            val solution = Day17()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(1638, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(17, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(4, CustomDay17("20\n15\n10\n5\n5").produceFirstAnswer(25))
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(3, CustomDay17("20\n15\n10\n5\n5").produceSecondAnswer(25))
                }
            }
        }
    }

    private class CustomDay17(val customData: String) : Day17() {
        override val data = this.customData
    }
}
