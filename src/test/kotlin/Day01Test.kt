import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val testInput = readInput("Day01_test")
    private val input = readInput("Day01")

    @Test
    fun `part 1`() {
        day01Part1(testInput) shouldBe 24000
        day01Part1(input) shouldBe 68292
    }

    @Test
    fun `part 2`() {
        day01Part2(testInput) shouldBe 45000
        day01Part2(input) shouldBe 203203
    }
}