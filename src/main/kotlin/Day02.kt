import RockPaperScissors.PAPER
import RockPaperScissors.ROCK
import RockPaperScissors.SCISSORS

enum class RockPaperScissors(
    private val scoreForUsing: Int,
) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun beats() =
        when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

    fun isBeatenBy() =
        values().first { it.beats(this) }

    private fun beats(other: RockPaperScissors) = beats() == other


    fun scoreAgainst(other: RockPaperScissors) =
        when {
            other.beats(this) -> 0
            this == other -> 3
            this.beats(other) -> 6
            else -> throw IllegalArgumentException()
        } + scoreForUsing
}


fun Char.parse() = when (this) {
    'A' -> ROCK
    'B' -> PAPER
    'C' -> SCISSORS
    else -> throw IllegalArgumentException("not a valid char: $this")
}

fun List<String>.determineScore(extractYou: (Char, RockPaperScissors) -> RockPaperScissors) =
    this.sumOf { line ->
        val other = line[0].parse()
        val you = extractYou(line[2], other)
        you.scoreAgainst(other)
    }

fun day02Part1(input: List<String>): Int =
    input.determineScore { c, _ ->
        when (c) {
            'X' -> ROCK
            'Y' -> PAPER
            'Z' -> SCISSORS
            else -> throw IllegalArgumentException()
        }
    }

fun day02Part2(input: List<String>): Int =
    input.determineScore { c, other ->
        when (c) {
            'X' -> other.beats()
            'Y' -> other
            'Z' -> other.isBeatenBy()
            else -> throw IllegalArgumentException()
        }
    }
