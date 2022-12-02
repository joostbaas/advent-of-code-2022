enum class RockPaperScissors(
    private val scoreForUsing: Int,
) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun isBeatenBy() =
        values().first { it.beats(this) }

    fun beats(other: RockPaperScissors) = beats() == other

    fun beats() =
        when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

    fun scoreAgainst(other: RockPaperScissors) =
        when {
            other.beats(this) -> 0
            this == other -> 3
            this.beats(other) -> 6
            else -> throw IllegalArgumentException()
        } + scoreForUsing


    companion object {
        fun parse(c: Char) =
            when (c) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw IllegalArgumentException("not a valid char: $c")
            }
    }

}


fun Char.parse() = RockPaperScissors.parse(this)

fun day02Part1(input: List<String>): Int =
    input.sumOf { line ->
        val other = line[0].parse()
        val you = line[2].parse()
        you.scoreAgainst(other)
    }

fun day02Part2(input: List<String>): Int =
    input.sumOf { line ->
        val other = line[0].parse()
        val you = when (line[2]) {
            'X' -> other.beats()
            'Y' -> other
            'Z' -> other.isBeatenBy()
            else -> throw IllegalArgumentException()
        }
        you.scoreAgainst(other)
    }
