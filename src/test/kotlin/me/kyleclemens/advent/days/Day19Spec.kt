package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day19Spec : MavenSpek() {
    override fun test() {
        given("a Day 19 solution") {
            val solution = Day19()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(576, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(207, answers.second)
                }
            }
        }
        // The tests for the first examples worked, but the second part's solution broke that.
    }

    private class CustomDay19(val customData: String) : Day19() {
        override val data = this.customData
    }
}
