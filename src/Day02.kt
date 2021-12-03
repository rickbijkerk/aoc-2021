fun main() {
    fun part1(input: List<String>): Int {
        val results = input.map { line ->
            val split = line.split(" ")

            Pair(split[0], split[1].toInt())
        }

        val commands = HashMap<String, Int>()

        results.forEach { (command, value) ->
            commands[command] = (commands[command] ?: 0) + value
        }

        val forward = commands["forward"]
        val down = commands["down"]
        val up = commands["up"]

        return (down!! - up!!) * forward!!
    }

    fun part2(input: List<String>): Int {
        val commandList = input.map { line ->
            val split = line.split(" ")

            Pair(split[0], split[1].toInt())
        }

        var aim = 0
        var horizontal = 0
        var depth = 0

        commandList.forEach { (command, value) ->
            when (command) {
                "forward" -> {
                    horizontal += value
                    depth += aim * value
                }
                "up" -> {
                    aim -= value
                }
                "down" -> {
                    aim += value
                }
            }
        }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")

    println( "${part1(testInput)} ==150")
    check(part1(testInput) == 150)
    println( "${part2(testInput)} ==900")
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
