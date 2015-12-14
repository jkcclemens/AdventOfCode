package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day14Spec : MavenSpek() {
    override fun test() {
        given("a Day 14 solution") {
            val solution = Day14()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(2660, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(1256, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(1120, CustomDay14("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\nDancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.").produceFirstAnswer(1000))
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(689, CustomDay14("Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\nDancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.").produceSecondAnswer(1000))
                }
            }
        }
    }

    private class CustomDay14(val customData: String) : Day14() {
        override val data = this.customData
    }
}
