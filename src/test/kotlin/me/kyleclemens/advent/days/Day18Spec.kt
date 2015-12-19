package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day18Spec : MavenSpek() {
    override fun test() {
        given("a Day 18 solution") {
            val solution = Day18()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(768, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(781, answers.second)
                }
            }
        }
    }
}
