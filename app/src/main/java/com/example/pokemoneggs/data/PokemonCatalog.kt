package com.example.pokemoneggs.data

data class Species(
    val dex: Int,
    val name: String,
    val evolvesToDex: Int? = null,
    val evolvesAtLevel: Int? = null
)

object PokemonCatalog {
    // Compact Gen 1 subset with evolutions (extend freely)
    val species = listOf(
        Species(1, "Bulbasaur", 2, 16),
        Species(2, "Ivysaur", 3, 32),
        Species(3, "Venusaur"),
        Species(4, "Charmander", 5, 16),
        Species(5, "Charmeleon", 6, 36),
        Species(6, "Charizard"),
        Species(7, "Squirtle", 8, 16),
        Species(8, "Wartortle", 9, 36),
        Species(9, "Blastoise"),
        Species(10, "Caterpie", 11, 7),
        Species(11, "Metapod", 12, 10),
        Species(12, "Butterfree"),
        Species(13, "Weedle", 14, 7),
        Species(14, "Kakuna", 15, 10),
        Species(15, "Beedrill"),
        Species(16, "Pidgey", 17, 18),
        Species(17, "Pidgeotto", 18, 36),
        Species(18, "Pidgeot"),
        Species(19, "Rattata", 20, 20),
        Species(20, "Raticate"),
        Species(21, "Spearow", 22, 20),
        Species(22, "Fearow"),
        Species(23, "Ekans", 24, 22),
        Species(24, "Arbok"),
        Species(25, "Pikachu", 26, 30), // simplified (stone in canon)
        Species(26, "Raichu"),
        Species(27, "Sandshrew", 28, 22),
        Species(28, "Sandslash"),
        Species(29, "Nidoran♀", 30, 16),
        Species(30, "Nidorina", 31, 32),
        Species(31, "Nidoqueen"),
        Species(32, "Nidoran♂", 33, 16),
        Species(33, "Nidorino", 34, 32),
        Species(34, "Nidoking"),
        Species(35, "Clefairy", 36, 25),
        Species(36, "Clefable"),
        Species(37, "Vulpix", 38, 25),
        Species(38, "Ninetales"),
        Species(39, "Jigglypuff", 40, 25),
        Species(40, "Wigglytuff")
    )

    private val byDex = species.associateBy { it.dex }

    fun byDex(dex: Int): Species = byDex[dex] ?: Species(dex, "Unknown")

    fun randomBasicDex(seed: Long): Int {
        val basics = listOf(1,4,7,10,13,16,19,21,23,25,27,29,32,35,37,39)
        val r = java.util.Random(seed)
        return basics[r.nextInt(basics.size)]
    }
}
