import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day02KtTest {

    private val testInput = readInput("Day02_test")
    private val input = readInput("Day02")

    @Test
    fun `part 1`() {
        day02Part1(testInput) shouldBe 15
        day02Part1(input) shouldBe 13526
    }


    @Test
    fun `part 2`() {
        day02Part2(testInput) shouldBe 12
        day02Part2(input) shouldBe 14204
    }
}
