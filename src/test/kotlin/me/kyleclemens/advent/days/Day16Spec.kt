package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day16Spec : MavenSpek() {
    override fun test() {
        given("a Day 16 solution") {
            val solution = Day16()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(213, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(323, answers.second)
                }
            }
        }
    }

}
