import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day07Test {

    private val testInput = readInput("Day07_test")
    private val input = readInput("Day07")

    @Test
    fun `part 1`() {
        day07Part1(testInput) shouldBe 95437
        day07Part1(input) shouldBe 1555642
    }

    @Test
    fun `part 2`() {
        day07Part2(testInput) shouldBe 24933642
        day07Part2(input) shouldBe 5974547
    }
}
