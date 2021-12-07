import java.math.BigDecimal

fun main() {

    fun part1(input: List<String>): Int {
        val startingValues = input[0]
            .split(",")
            .map { it.toInt() }.toMutableList()

        var day = 0
        val fishToAdd = arrayListOf<Int>()

        while (day < 80) {
            startingValues.forEachIndexed { index, fish ->
                if (fish == 0) {
                    fishToAdd.add(8)
                    startingValues[index] = 6
                } else {
                    startingValues[index]--
                }
            }
            day++
            startingValues.addAll(fishToAdd)
            fishToAdd.clear()
        }

        return startingValues.size
    }

    fun part2(input: List<String>): BigDecimal {
        val fishHashMap = HashMap<Int, BigDecimal>()
        IntRange(0, 8).forEach { fishHashMap[it] = BigDecimal(0) } // set defaults

        input[0].split(",")
            .map { it.toInt() }
            .forEach { fish ->
                fishHashMap[fish] = fishHashMap[fish]!! + BigDecimal(1)
            }

        var day = 0
        var fishToAdd = BigDecimal(0)
        while (day < 256) {
            fishHashMap.forEach { (age, numberOfFish) ->
                when (age) {
                    0 -> {
                        fishToAdd = numberOfFish
                        fishHashMap[0] = BigDecimal(0)
                    }
                    else -> {
                        fishHashMap[age - 1] = numberOfFish
                        fishHashMap[age] = BigDecimal(0)
                    }
                }
            }

            fishHashMap[6] = fishHashMap[6]!! + fishToAdd
            fishHashMap[8] = fishHashMap[8]!! + fishToAdd

            day++
            fishToAdd = BigDecimal(0)
        }

        return fishHashMap
            .map { (_, value) -> value }
            .sumOf { value -> value }
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    println("${part1(testInput)} ==5934")
    check(part1(testInput) == 5934)
    println(part1(input))

    println("=========================")

    println("${part2(testInput)} ==26984457539")
    check(part2(testInput) == BigDecimal(26984457539))
    println(part2(input))
}
