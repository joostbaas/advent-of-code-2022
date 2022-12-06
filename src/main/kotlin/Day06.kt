
fun String.indexOfFirstNDifferentChars(count: Int): Int =
    this.windowed(count).indexOfFirst { it.hasAllDifferentCharacters() } + count

fun String.hasAllDifferentCharacters() = this.toSet().size == length

fun day06Part1(input: String): Int =
    input.indexOfFirstNDifferentChars(4)

fun day06Part2(input: String): Int =
    input.indexOfFirstNDifferentChars(14)