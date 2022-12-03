import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day03Test {

    private val testInput = readInput("Day03_test")
    private val input = readInput("Day03")

    @Test
    fun `part 1`() {
        day03Part1(listOf("vJrwpWtwJgWrhcsFMMfFFhFp")) shouldBe 16
        day03Part1(listOf("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")) shouldBe 38
        day03Part1(testInput) shouldBe 157
        day03Part1(input) shouldBe 8185
    }


    @Test
    fun `part 2`() {
        day03Part2(testInput) shouldBe 70
        day03Part2(input) shouldBe 2817
    }
}
