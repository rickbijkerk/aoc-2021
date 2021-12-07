import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val startPositions = input[0].split(",").map { x -> x.toInt() }

        val highestPosition = startPositions.maxOrNull()!!

        val positionAndCost = IntRange(0, highestPosition).map { position ->
            val cost = startPositions.sumOf { crabPosition -> abs(position - crabPosition) }
            Pair(position, cost)
        }

        return positionAndCost.minByOrNull { (_, cost) -> cost }!!.second
    }

    fun part2(input: List<String>): Int {
        val startPositions = input[0].split(",").map { x -> x.toInt() }

        val highestPosition = startPositions.maxOrNull()!!

        val positionAndCost = IntRange(0, highestPosition).map { position ->
            val cost = startPositions.sumOf { crabPosition ->
                val distance = abs(position - crabPosition)
                IntRange(0, distance).sum()
            }
            Pair(position, cost)
        }

        return positionAndCost.minByOrNull { (_, cost) -> cost }!!.second
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    println("${part1(testInput)} ==37")
    check(part1(testInput) == 37)
    println(part1(input))

    println("=========================")

    println("${part2(testInput)} ==168")
    check(part2(testInput) == 168)
    println(part2(input))
}
