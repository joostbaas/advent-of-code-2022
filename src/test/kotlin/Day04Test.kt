import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day04Test {

    private val testInput = readInput("Day04_test")
    private val input = readInput("Day04")

    @Test
    fun `part 1`() {
        day04Part1(testInput) shouldBe 2
        day04Part1(input) shouldBe 657
    }


    @Test
    fun `part 2`() {
        day04Part2(testInput) shouldBe 4
        day04Part2(input) shouldBe 938
    }
}
