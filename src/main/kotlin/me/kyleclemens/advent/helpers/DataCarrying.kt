package me.kyleclemens.advent.helpers

import java.io.File

interface DataCarrying {
    val data: String
        get() {
            val usesData = this.javaClass.getDeclaredAnnotation(UsesData::class.java) ?: throw IllegalStateException("Class was not annotated with UsesData")
            return File("src/main/resources/day${usesData.day}").readText()
        }

    val splitData: List<String>
        get() {
            return this.data.split("\n").filterNot { it.isEmpty() }
        }
}
