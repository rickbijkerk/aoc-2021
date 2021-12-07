class WorldMap() {
    val map = Array(1000) { Array(1000) { 0 } }

    fun getDangerousAreas(): Int {
        var dangerousAreas = 0

        for (x in map.indices) {
            for (y in map.indices) {
                if (map[x][y] > 1) {
                    dangerousAreas++
                }
            }
        }
        return dangerousAreas
    }
}

data class Coordinate(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x,$y)"
    }
}

private val nonDiagonalFilter = { (from, to): Pair<Coordinate, Coordinate> -> from.x == to.x || from.y == to.y }
private val diagonalLineFilter = { (from, to): Pair<Coordinate, Coordinate> -> from.x != to.x && from.y != to.y }

fun main() {

    fun toCoordinate(from: String): Coordinate {
        val split = from.split(",")
        return Coordinate(split[0].toInt(), split[1].toInt())
    }

    fun getLines(
        input: List<String>,
    ): List<Pair<Coordinate, Coordinate>> {
        return input.map { line ->
            val (from, to) = line.split(" -> ")
            Pair(toCoordinate(from), toCoordinate(to))
        }
    }

    fun markNonDiagonalLines(
        lineCoordinates: List<Pair<Coordinate, Coordinate>>,
        worldMap: WorldMap
    ) {
        lineCoordinates.forEach { (from, to) ->
            if (from.x == to.x) { // Mark vertical lines
                IntRange(minOf(from.y, to.y), maxOf(from.y, to.y)).forEach {
                    worldMap.map[from.x][it] += 1
                }
            } else { // Mark horizontal lines
                IntRange(minOf(from.x, to.x), maxOf(from.x, to.x)).forEach {
                    worldMap.map[it][from.y] += 1
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val nonDiagonalLines = getLines(input).filter(nonDiagonalFilter)

        val worldMap = WorldMap()
        markNonDiagonalLines(nonDiagonalLines, worldMap)

        return worldMap.getDangerousAreas()
    }

    fun part2(input: List<String>): Int {
        val horizontalLines = getLines(input).filter(nonDiagonalFilter)
        val diagonalLines = getLines(input).filter(diagonalLineFilter)

        val worldMap = WorldMap()
        markNonDiagonalLines(horizontalLines, worldMap)

        diagonalLines.forEach { (from, to) ->
            val lineLength = 1 + maxOf(from.y, to.y) - minOf(from.y, to.y) // Doesnt matter if you take x or y here
            var nextXCoordinate = from.x
            var nextYCoordinate = from.y
            for (i in 1..lineLength) {
                worldMap.map[nextXCoordinate][nextYCoordinate] += 1
                println("marking next coordinate: $nextXCoordinate, $nextYCoordinate")
                if (from.x < to.x) {
                    nextXCoordinate++
                } else {
                    nextXCoordinate--
                }

                if (from.y < to.y) {
                    nextYCoordinate++
                } else {
                    nextYCoordinate--
                }
            }
        }

        return worldMap.getDangerousAreas()
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    println("${part1(testInput)} ==5")
    check(part1(testInput) == 5)
    println(part1(input))

    println("=========================")

    println("${part2(testInput)} ==12")
    check(part2(testInput) == 12)
    println(part2(input))
}
