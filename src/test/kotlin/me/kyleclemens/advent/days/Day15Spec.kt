package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day15Spec : MavenSpek() {
    override fun test() {
        given("a Day 15 solution") {
            val solution = Day15()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(13882464, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(11171160, answers.second)
                }
            }
        }
    }

}
