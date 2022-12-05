typealias AssignedSections = IntRange

fun readIntoPairOfSectionAssignments(line: String): Pair<AssignedSections, AssignedSections> {
    val (first, second) = line.split(',').map { range ->
        val (begin, end) = range.split('-').map { it.toInt() }
        (begin..end)
    }
    return first to second
}

fun Pair<AssignedSections, AssignedSections>.haveOverlap(): Boolean =
    first.any { second.contains(it) }

fun oneFullyContainsOther(assignedSectionsPair: Pair<AssignedSections, AssignedSections>): Boolean {
    infix fun AssignedSections.fullyContains(other: AssignedSections) = other.all { this.contains(it) }
    val (first, second) = assignedSectionsPair
    return first fullyContains second || second fullyContains first
}

fun day04Part1(input: List<String>): Int =
    input.map(::readIntoPairOfSectionAssignments)
        .count(::oneFullyContainsOther)

fun day04Part2(input: List<String>): Int =
    input.map(::readIntoPairOfSectionAssignments)
        .count { it.haveOverlap() }
