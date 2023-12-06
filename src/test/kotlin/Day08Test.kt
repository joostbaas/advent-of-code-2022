import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day08Test {

    private val testInput = readInput("Day08_test")
    private val input = readInput("Day08")

    @Test
    fun `part 1`() {
        day08Part1(testInput) shouldBe 21
        day08Part1(input) shouldBe 1792
    }

    @Test
    fun `part 2`() {
        day08Part2(testInput) shouldBe 8
        day08Part2(input) shouldBe 334880
    }
}
