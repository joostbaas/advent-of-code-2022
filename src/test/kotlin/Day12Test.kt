import Day12.Companion.day12Part1
import Day12.Companion.day12Part2
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test

internal class Day12Test {

    private val testInput = readInput("Day12_test")
    private val input = readInput("Day12")

    @TestFactory
    fun `part 1`() = listOf(
        DynamicTest.dynamicTest("example") { day12Part1(testInput) shouldBe 31 },
        DynamicTest.dynamicTest("answer") { day12Part1(input) shouldBe 350 },
    )


    @Test
    fun `part 2`() {
        day12Part2(testInput) shouldBe 29
        day12Part2(input) shouldBe 349
    }
}
