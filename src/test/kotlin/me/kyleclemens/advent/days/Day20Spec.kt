package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day20Spec : MavenSpek() {
    override fun test() {
        given("a Day 20 solution") {
            val solution = Day20()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(665280, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(705600, answers.second)
                }
            }
        }
    }
}
