import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day05Test {

    private val testInput = readInput("Day05_test")
    private val input = readInput("Day05")

    @Test
    fun `part 1`() {
        day05Part1(testInput) shouldBe "CMZ"
        day05Part1(input) shouldBe "PTWLTDSJV"
    }


    @Test
    fun `part 2`() {
        day05Part2(testInput) shouldBe "MCD"
        day05Part2(input) shouldBe "WZMFVGGZP"
    }

}
