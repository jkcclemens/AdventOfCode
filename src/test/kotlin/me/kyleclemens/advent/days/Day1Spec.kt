package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day1Spec : MavenSpek() {
    override fun test() {
        given("a Day 1 solution") {
            val solution = Day1()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    assertEquals(280, answers.first)
                }
                it("should have the correct second answer") {
                    assertEquals(1797, answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(0, "(())".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "()()".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(3, "(((".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(3, "(()(()(".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(3, "))(((((".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(-1, "())".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(-1, "))(".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(-3, ")))".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(-3, ")())())".answers.first)
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(1, ")".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(5, "()())".answers.second)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Int>
        get() = CustomDay1(this).answers

    private class CustomDay1(val customData: String) : Day1() {
        override val data = this.customData
    }
}
