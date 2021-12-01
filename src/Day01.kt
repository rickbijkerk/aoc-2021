fun main() {
    fun part1(input: List<String>): Int {

        var result = 0

        input.forEachIndexed { index, string ->
            if (index != 0) {
                if (string.toInt() > input[index - 1].toInt()) {
                    result++
                }
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val numberList = input.map { it.toInt() }

        var result = 0

        numberList.forEachIndexed { index, value ->
            if (numberList.size - 1 > index + 2) {
                val currentWindow = value + numberList[index + 1] + numberList[index + 2]
                val nextWindow = currentWindow - value + numberList[index + 3]
                if (nextWindow > currentWindow) {
                    result++
                }
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    assert(part1(testInput) == 6)
    assert(part2(testInput) == 2)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
