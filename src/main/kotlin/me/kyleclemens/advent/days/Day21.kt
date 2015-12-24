package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import java.util.Collections

@UsesData(day = 21)
class Day21 : Solution {

    private val items: Set<Item>
        get() {
            val items = this.data.split("\n\n")[0].split("\n")
            return items
                .map {
                    val parts = it.split(Regex("\\s+"))
                    val type = when (parts[0]) {
                        "Armor:" -> ItemType.ARMOR
                        "Weapon:" -> ItemType.WEAPON
                        "Ring:" -> ItemType.RING
                        else -> throw IllegalArgumentException("Bad item type")
                    }
                    val name = parts[1]
                    val cost = parts[2].toInt()
                    val strength = parts[3].toInt()
                    val defense = parts[4].toInt()
                    Item(name, defense, strength, cost, type)
                }
                .toSet()
        }

    private val separatedItems: Map<ItemType, List<Item>>
        get() {
            return this.items.fold(hashMapOf<ItemType, List<Item>>()) { map, item ->
                map[item.type] = (map[item.type] ?: listOf()) + item
                map
            }
        }

    private val boss: Boss
        get() {
            val parts = this.data.split("\n\n")[1].split("\n")
            val hp = parts[0].split(" ")[2].toInt()
            val strength = parts[1].split(" ")[1].toInt()
            val defense = parts[2].split(" ")[1].toInt()
            return Boss(hp, defense, strength)
        }

    enum class ItemType(val allowed: Int) {
        WEAPON(1),
        ARMOR(1),
        RING(2)
    }

    data class Item(
        val name: String,
        val defense: Int,
        val strength: Int,
        val cost: Int,
        val type: ItemType
    )

    interface Combatant {
        var hp: Int
        val strength: Int
        val defense: Int
    }

    open class Player : Combatant {
        override var hp: Int = 100

        private val _equipment: MutableSet<Item> = hashSetOf()
        val equipment: Set<Item>
            get() = Collections.unmodifiableSet(this._equipment)

        override val strength: Int
            get() = this.equipment.map { it.strength }.sum()
        override val defense: Int
            get() = this.equipment.map { it.defense }.sum()

        fun equip(vararg items: Item?) {
            for (item in items.filterNotNull()) {
                require(this.equipment.count { item.type == it.type } + 1 <= item.type.allowed)
                this._equipment.add(item)
            }
        }

        // NOTE: There was an unequip here, but it's gone now.
    }

    data class Boss(override var hp: Int, override val defense: Int, override val strength: Int) : Combatant

    class Battle(val attacker: Combatant, val defender: Combatant) {
        var attackerTurn = true
        var _winner: Combatant? = null
        val winner: Combatant
            get() {
                if (this._winner == null) {
                    this.processAllTurns()
                    this._winner = if (this.attacker.hp > 0) this.attacker else this.defender
                }
                return this._winner ?: throw IllegalStateException("Set to null by another thread")
            }

        private fun processTurn() {
            val attacker = if (this.attackerTurn) this.attacker else this.defender
            val defender = if (this.attackerTurn) this.defender else this.attacker
            defender.hp -= Math.max(1, attacker.strength - defender.defense)
            this.attackerTurn = !this.attackerTurn
        }

        private fun processAllTurns() {
            while (this.attacker.hp > 0 && this.defender.hp > 0) {
                this.processTurn()
            }
        }

    }

    private fun determineCosts(win: Boolean): Set<Int> {
        val items = this.separatedItems
        val rings = items[ItemType.RING]!! + null
        val armors = items[ItemType.ARMOR]!! + null
        val costs = hashSetOf<Int>()
        for (weapon in items[ItemType.WEAPON]!!) {
            for (armor in armors) {
                for (ring1 in rings) {
                    for (ring2 in rings) {
                        if (ring1 === ring2) continue // avoid double rings
                        val equipment = listOf(weapon, armor, ring1, ring2)
                        val player = Player().apply { this.equip(*equipment.toTypedArray()) }
                        val battle = Battle(player, this.boss)
                        if (battle.winner === player && win || battle.winner !== player && !win) {
                            costs.add(equipment.filterNotNull().map { it.cost }.sum())
                        }
                    }
                }
            }
        }
        return costs
    }

    private fun produceFirstAnswer() = this.determineCosts(true).min()!!

    private fun produceSecondAnswer() = this.determineCosts(false).max()!!

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
