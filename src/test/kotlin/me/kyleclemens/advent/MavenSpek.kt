package me.kyleclemens.advent

import org.jetbrains.spek.api.Spek

abstract class MavenSpek : Spek() {

    abstract fun test()

    init {
        this.test()
    }

}
