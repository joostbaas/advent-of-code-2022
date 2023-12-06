import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day09Test {

    private val testInput = readInput("Day09_test")
    private val input = readInput("Day09")

    @Test
    fun `part 1`() {
        day09Part1(testInput) shouldBe 13
        day09Part1(input) shouldBe 6314
    }

    @Test
    fun testDoubleExt() {
//        Point(0, 0).average { x }
    }

    @Test
    fun `part 2`() {
        day09Part2(testInput) shouldBe 1

        day09Part2(
            listOf(
                "R 5",
                "U 8",
                "L 8",
                "D 3",
                "R 17",
                "D 10",
                "L 25",
                "U 20",
            )
        ) shouldBe 36

        day09Part2(input) shouldBe 2504
    }
}
