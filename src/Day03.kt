fun main() {
    fun part1(input: List<String>): Int {
        var gammaRateBinary = ""

        for (i in 0 until input[0].length) {
            val gammaNumberRaw = input.fold("") { sum, element ->
                sum + element[i].toString()
            }

            val countZero = gammaNumberRaw.count { x -> x == '0' }
            val countOne = gammaNumberRaw.count { x -> x == '1' }
            val gammaNumber = when {
                countOne > countZero -> 1
                else -> 0
            }

            gammaRateBinary += gammaNumber
        }
        val gammaRate = gammaRateBinary.toInt(2)

        val epsilonRateBinary = gammaRateBinary.map { value ->
            if (value == '1') "0" else "1"
        }.joinToString(separator = "")

        val epsilonRate = epsilonRateBinary.toInt(2)


        return gammaRate * epsilonRate
    }

    fun goThroughList(
        input: List<String>,
        predicate: (List<String>, List<String>) -> List<String>
    ): Int {
        var workingList = input
        for (i in 0 until workingList[0].length) {
            if (workingList.size == 1) break
            else {
                val zeroDominantList = workingList.filter { value -> value[i] == '0' }
                val oneDominantList = workingList.filter { value -> value[i] == '1' }

                workingList = predicate(zeroDominantList, oneDominantList)
            }
        }
        return workingList.first().toInt(2)
    }

    fun part2(input: List<String>): Int {
        val oxygenRating =
            goThroughList(input.toList()) { zeroDominantList: List<String>, oneDominantList: List<String> ->
                if (zeroDominantList.size > oneDominantList.size) zeroDominantList else oneDominantList
            }
        val co2ScrubberRating =
            goThroughList(input.toList()) { zeroDominantList: List<String>, oneDominantList: List<String> ->
                if (zeroDominantList.size <= oneDominantList.size) zeroDominantList else oneDominantList
            }


        return oxygenRating * co2ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")

    println("${part1(testInput)} ==198")
    check(part1(testInput) == 198)
    println("${part2(testInput)} ==230")
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
