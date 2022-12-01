fun main() {
    fun caloriesPerElf(input: List<String>) =
        input.fold(listOf(0)) { acc, line ->
            if (line.isEmpty()) {
                acc.plusElement(0)
            } else {
                val currentElf = acc.last()
                val previousElves: List<Int> = acc.dropLast(1)
                val newCurrentElf = currentElf + line.toInt()
                previousElves.plusElement(newCurrentElf)
            }
        }

    fun part1(input: List<String>): Int =
        caloriesPerElf(input).max()

    fun part2(input: List<String>): Int =
        caloriesPerElf(input).sortedDescending().take(3).sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    val input = readInput("Day01")
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(input))
}
