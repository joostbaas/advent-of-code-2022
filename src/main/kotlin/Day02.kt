sealed class RockPaperScissors(
    private val score: Int,
) {

    fun isBeatenBy() =
        when (this) {
             SCISSORS -> ROCK
             ROCK -> PAPER
             PAPER -> SCISSORS
        }

    fun beats() =
        when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

    fun scoreAgainst(other: RockPaperScissors) =
        when {
            other.beats() == this -> 0
            this == other -> 3
            this.beats() == other -> 6
            else -> throw IllegalStateException()
        } + score


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

object ROCK : RockPaperScissors(1)
object PAPER : RockPaperScissors(2)
object SCISSORS : RockPaperScissors(3)

fun Char.parse() = RockPaperScissors.parse(this)

fun day02Part1(input: List<String>): Int =
    input.sumOf { line ->
        val (other, you) = line.toCharArray().filterNot { it == ' ' }.map { it.parse() }
        you.scoreAgainst(other)
    }


fun day02Part2(input: List<String>): Int =
    input.sumOf { line ->
        val other = line[0].parse()
        val you = when(line[2]) {
            'X' -> other.beats()
            'Y' -> other
            'Z' -> other.isBeatenBy()
            else -> throw IllegalArgumentException()
        }
        you.scoreAgainst(other)
    }
