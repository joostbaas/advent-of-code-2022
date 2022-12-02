
fun List<Int>.addToLast(e: Int) = this.dropLast(1).plusElement(last() + e)

fun day01Part1(input: List<String>) = caloriesPerElf(input).max()

fun day01Part2(input: List<String>): Int =
    caloriesPerElf(input).sortedDescending().take(3).sum()

fun caloriesPerElf(input: List<String>) =
    input.fold(listOf(0)) { acc, line ->
        when (line){
            "" -> acc.plusElement(0)
            else -> acc.addToLast(line.toInt())
        }
    }

