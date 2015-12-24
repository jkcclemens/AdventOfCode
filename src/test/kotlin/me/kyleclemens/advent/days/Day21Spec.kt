package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day21Spec : MavenSpek() {
    override fun test() {
        given("a Day 21 solution") {
            val solution = Day21()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(111, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(188, answers.second)
                }
            }
        }
    }
}
