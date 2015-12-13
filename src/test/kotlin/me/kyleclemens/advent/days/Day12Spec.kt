package me.kyleclemens.advent.days

import me.kyleclemens.advent.MavenSpek
import kotlin.test.assertEquals

class Day12Spec : MavenSpek() {
    override fun test() {
        given("a Day 12 solution") {
            val solution = Day12()
            on("producing the answers") {
                val answers = solution.answers
                it("should have the correct first answer") {
                    //                    assertEquals("cqjxxyzz", answers.first)
                }
                it("should have the correct second answer") {
                    //                    assertEquals("cqkaabcc", answers.second)
                }
            }
        }
        given("examples") {
            on("testing part 1") {
                it("should produce the correct answer") {
                    assertEquals(6, "[1,2,3]".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(6, """{"a":2,"b":4}""".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(3, "[[[3]]]".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(3, """{"a":{"b":4},"c":-1}""".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, """{"a":[-1,1]}""".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, """[-1,{"a":1}]""".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "[]".answers.first)
                }
                it("should produce the correct answer") {
                    assertEquals(0, "{}".answers.first)
                }
            }
            on("testing part 2") {
                it("should produce the correct answer") {
                    assertEquals(6, "[1,2,3]".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(4, """[1,{"c":"red","b":2},3]""".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(0, """{"d":"red","e":[1,2,3,4],"f":5}""".answers.second)
                }
                it("should produce the correct answer") {
                    assertEquals(6, """[1,"red",5]""".answers.second)
                }
            }
        }
    }

    private val String.answers: Pair<Int, Any>
        get() = CustomDay12(this).answers

    private class CustomDay12(val customData: String) : Day12() {
        override val data = this.customData
    }
}
