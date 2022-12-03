fun Char.priorityValue(): Int = when (this) {
    in ('a'..'z') -> this - 'a' + 1
    in ('A'..'Z') -> this - 'A' + 27
    else -> throw IllegalArgumentException()
}

fun charsInCommon(vararg s: String): Set<Char> =
    s.toList().fold(s[0].toSet()) { acc, next ->
        acc intersect next.toSet()
    }

fun <T> Set<T>.onlyElement(): T = if (size != 1) throw IllegalArgumentException() else first()

fun day03Part1(input: List<String>): Int = input.sumOf { line ->
    val (firstHalf, secondHalf) = line.chunked(line.length / 2)
    val charThatAppearsInBothHalves = charsInCommon(firstHalf,  secondHalf)
    charThatAppearsInBothHalves.onlyElement().priorityValue()
}

fun day03Part2(input: List<String>): Int = input.chunked(3)
    .sumOf { group: List<String> ->
        val (chunk0, chunk1, chunk2) = group
        val charsThatAppearsInAllThree = charsInCommon(chunk0, chunk1, chunk2)
        charsThatAppearsInAllThree.onlyElement().priorityValue()
    }
