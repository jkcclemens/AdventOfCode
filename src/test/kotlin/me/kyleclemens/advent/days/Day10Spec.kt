package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day10Spec : MavenSpek() {
    override fun test() {
        given("a Day 10 solution") {
            val solution = Day10()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(329356, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(4666278, answers.second)
                }
            }
        }
    }

}
