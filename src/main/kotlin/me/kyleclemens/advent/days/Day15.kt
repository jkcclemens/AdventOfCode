package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 15)
open class Day15 : Solution {

    /**
     * Data class representing an ingredient and its properties.
     */
    private data class Ingredient(
        val name: String,
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int
    )

    /**
     * Converts this string into an [Ingredient].
     */
    private fun String.toIngredient(): Ingredient {
        // Split by spaces
        val split = this.replace(",", "").split(" ")
        // Find the stuff
        return Ingredient(
            split[0].let { it.substring(0, it.length - 1) },
            split[2].toInt(),
            split[4].toInt(),
            split[6].toInt(),
            split[8].toInt(),
            split[10].toInt()
        )
    }

    /**
     * A fresh list of ingredients, every time.
     */
    private val ingredients: List<Ingredient>
        get() = this.splitData.map { it.toIngredient() }

    private fun produceFirstAnswer(): Int {
        // God, I hate everything
        val ingredients = this.ingredients
        // Brute forcing this because a dynamic algorithm takes a long goddamn time and you should NOT waste hours in
        // the early morning trying to do that and learning about number partitions and then taking permutations of that
        // all to make a dynamic answer to a static problem. Don't do it, kids. Just don't.
        check(ingredients.size == 4)
        // why not
        val scores = arrayListOf<Int>()
        // I could probably turn this into some sort of fluent bullshit or something, but I don't have the will to li--
        // I mean I don't have the willpower to think about how to do that right now
        // SO LOOP THROUGH ALL THE POSSIBLE COMBINATIONS AND JUST BRUTE FORCE IT
        for (i in 1..100) {
            for (j in 1..100 - i) {
                for (k in 1..100 - i - j) {
                    val l = 100 - i - j - k
                    // do math
                    val capacity = Math.max(0, ingredients[0].capacity * i + ingredients[1].capacity * j + ingredients[2].capacity * k + ingredients[3].capacity * l)
                    val durability = Math.max(0, ingredients[0].durability * i + ingredients[1].durability * j + ingredients[2].durability * k + ingredients[3].durability * l)
                    val flavor = Math.max(0, ingredients[0].flavor * i + ingredients[1].flavor * j + ingredients[2].flavor * k + ingredients[3].flavor * l)
                    val texture = Math.max(0, ingredients[0].texture * i + ingredients[1].texture * j + ingredients[2].texture * k + ingredients[3].texture * l)
                    // more math
                    scores += capacity * durability * flavor * texture
                }
            }
        }
        // the biggest one is the weiner
        return scores.max()!!
    }

    private fun produceSecondAnswer(): Int {
        // It's the same thing as above. Extract it? Fuck you.
        val ingredients = this.ingredients
        check(ingredients.size == 4)
        val scores = arrayListOf<Int>()
        for (i in 1..100) {
            for (j in 1..100 - i) {
                for (k in 1..100 - i - j) {
                    val l = 100 - i - j - k
                    val capacity = Math.max(0, ingredients[0].capacity * i + ingredients[1].capacity * j + ingredients[2].capacity * k + ingredients[3].capacity * l)
                    val durability = Math.max(0, ingredients[0].durability * i + ingredients[1].durability * j + ingredients[2].durability * k + ingredients[3].durability * l)
                    val flavor = Math.max(0, ingredients[0].flavor * i + ingredients[1].flavor * j + ingredients[2].flavor * k + ingredients[3].flavor * l)
                    val texture = Math.max(0, ingredients[0].texture * i + ingredients[1].texture * j + ingredients[2].texture * k + ingredients[3].texture * l)
                    // Eye spy with my little eye the difference. Calculate calories because FUCKING MARY _NEEDS_ a
                    // cookie with SPECIFICALLY 500 calories because she's watching her figure, I guess.
                    val calories = Math.max(0, ingredients[0].calories * i + ingredients[1].calories * j + ingredients[2].calories * k + ingredients[3].calories * l)
                    // If the calories aren't 500, throw the cookie in the garbage. Toss it right in. No one cares. All
                    // of life is doomed in the end anyway. What even matters anymore?
                    if (calories != 500) continue
                    // fuck
                    scores += capacity * durability * flavor * texture
                }
            }
        }
        // WINNER WINNER CHICKEN DINNER!!
        return scores.max()!!
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()

}
