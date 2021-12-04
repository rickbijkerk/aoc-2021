data class BingoCard(
    val card: List<List<Int>>
)

fun main() {

    fun getBingoCards(input: List<String>): ArrayList<BingoCard> {
        var startIndex = 1
        val bingoCards = arrayListOf<BingoCard>()
        while (input.size > startIndex) {
            startIndex += 1
            val horizontalNumbers = input.subList(startIndex, startIndex + 5)
                .map { line ->
                    line.split(" ")
                        .filter(String::isNotEmpty)
                        .map { string -> string.toInt() }
                }
            bingoCards.add(BingoCard(horizontalNumbers))

            startIndex += 5
        }
        return bingoCards
    }

    fun getWinner(
        bingoCards: ArrayList<BingoCard>,
        drawnNumbers: MutableList<Int>,
        inputNumbers: List<Int>
    ): BingoCard? {
        val winner: BingoCard?
        while (true) {
            val winnerHorizontal = bingoCards.find { bingoCard ->
                bingoCard.card.find { bingoCardLine ->
                    drawnNumbers.containsAll(bingoCardLine)
                } != null

            }
            val winnerVertical = bingoCards.find { bingoCard ->
                val verticalLines = IntRange(0, 4).map { i -> bingoCard.card.map { line -> line[i] } }
                verticalLines.find { bingoCardLine ->
                    drawnNumbers.containsAll(bingoCardLine)
                } != null
            }
            if (winnerHorizontal != null) {
                winner = winnerHorizontal
                break
            } else if (winnerVertical != null) {
                winner = winnerVertical
                break
            } else {
                drawnNumbers.add(inputNumbers[drawnNumbers.size])
            }
        }
        return winner
    }

    fun part1(input: List<String>): Int {
        val inputNumbers = input[0].split(",").map { x -> x.toInt() }

        val bingoCards = getBingoCards(input)

        val drawnNumbers = inputNumbers.take(5).toMutableList()
        val winner: BingoCard? = getWinner(bingoCards, drawnNumbers, inputNumbers)

        val notDrawnNumbers = winner!!.card.map { bingoLine ->
            bingoLine.filter { number -> !drawnNumbers.contains(number) }
        }.flatten()

        val notDrawnNumbersSum = notDrawnNumbers.fold(0) { sum, element -> sum + element }

        return notDrawnNumbersSum * drawnNumbers.last().toInt()
    }


    fun part2(input: List<String>): Int {
        val inputNumbers = input[0].split(",").map { x -> x.toInt() }

        val bingoCards = getBingoCards(input)
        val drawnNumbers = inputNumbers.take(5).toMutableList()

        var worstCard: BingoCard? = null
        while (worstCard == null) {
            val winner = getWinner(bingoCards, drawnNumbers, inputNumbers)
            bingoCards.remove(winner)

            if (bingoCards.size == 0) {
                worstCard = winner
            }
        }

        val notDrawnNumbers = worstCard.card.map { bingoLine ->
            bingoLine.filter { number -> !drawnNumbers.contains(number) }
        }.flatten()
        val notDrawnNumbersSum = notDrawnNumbers.fold(0) { sum, element -> sum + element }


        return notDrawnNumbersSum * drawnNumbers.last()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    println("${part1(testInput)} ==4512")
    check(part1(testInput) == 4512)
    println(part1(input))

    println("=========================")

    println("${part2(testInput)} ==1924")
    check(part2(testInput) == 1924)
    println(part2(input))
}
