import kotlin.math.abs

sealed interface Instruction : (Int) -> List<Int> {
    override fun invoke(currentValue: Int): List<Int>
}

data class Add(val value: Int) : Instruction {
    override fun invoke(currentValue: Int): List<Int> = listOf(currentValue, currentValue + value)
}

object Noop : Instruction {
    override fun invoke(currentValue: Int): List<Int> = listOf(currentValue)
}

fun List<String>.parseInstruction(): List<Instruction> =
    map {
        if (it.startsWith("addx ")) Add(it.split(" ")[1].toInt())
        else Noop
    }

fun List<Instruction>.xValuesAfterEveryCycle(): List<Int> =
    fold(listOf(1)) { acc, instruction ->
        acc + instruction(acc.last())
    }

fun day10Part1(input: List<String>): Int =
    input.parseInstruction().xValuesAfterEveryCycle()
        .let {
            (20..220 step 40)
                .sumOf { cycle -> cycle * it[cycle - 1] }
        }

fun day10Part2(input: List<String>): String =
    input.parseInstruction().xValuesAfterEveryCycle()
        .chunked(40).take(6)
        .joinToString(separator = "\n") { line ->
            line.mapIndexed { index, spriteX ->
                if (abs(spriteX - index) <= 1) "#" else "."
            }.joinToString(separator = "")
        }