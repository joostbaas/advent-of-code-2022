fun Char.priorityValue(): Int = when (this) {
    in ('a'..'z') -> this - 'a' + 1
    in ('A'..'Z') -> this - 'A' + 27
    else -> throw IllegalArgumentException()
}

fun charsInCommon(s: List<String>): Set<Char> =
    s.fold(s[0].toSet()) { acc, next ->
        acc intersect next.toSet()
    }

fun <T> Set<T>.onlyElement(): T = if (size != 1) throw IllegalArgumentException() else first()

fun day03Part1(input: List<String>): Int = input.sumOf { line ->
    val halfStrings = line.chunked(line.length / 2)
    charsInCommon(halfStrings).onlyElement().priorityValue()
}

fun day03Part2(input: List<String>): Int = input.chunked(3)
    .sumOf { group: List<String> ->
        charsInCommon(group).onlyElement().priorityValue()
    }
