fun Char.priorityValue(): Int = when (this) {
    in ('a'..'z') -> this - 'a' + 1
    in ('A'..'Z') -> this - 'A' + 27
    else -> throw IllegalArgumentException()
}

fun day03Part1(input: List<String>): Int = input.sumOf { line ->
    val (firstHalf, secondHalf) = line.chunked(line.length / 2).map { chunk -> chunk.toSet() }
    val charThatAppearsInBothHalves = firstHalf intersect secondHalf
    charThatAppearsInBothHalves.first().priorityValue()
}

fun day03Part2(input: List<String>): Int = input.chunked(3)
    .map { group -> group.map { str -> str.toSet() } }
    .sumOf { group: List<Set<Char>> ->
        val (chunk0, chunk1, chunk2) = group
        val charThatAppearsInAllThree = chunk0 intersect chunk1 intersect chunk2
        charThatAppearsInAllThree.first().priorityValue()
    }
