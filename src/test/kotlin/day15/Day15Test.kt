package day15
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import readInput

internal class Day15Test {

    private val testInput = readInput("Day15_test")
    private val input = readInput("Day15")

    @Test
    fun parse() {
        "Sensor at x=2, y=18: closest beacon is at x=-2, y=15".parse() shouldBe
                SensorReading(Point(2, 18), Point(-2, 15))

    }


    @TestFactory
    fun `part 1`() = listOf(
        dynamicTest("example") { day15Part1(testInput, 10) shouldBe 26 },
        dynamicTest("answer ") { day15Part1(input, 2_000_000) shouldBe 4748135 },
    )

    @TestFactory
    fun `part 2`() = listOf(
        dynamicTest("example") { day15Part2(testInput, 20) shouldBe 56_000_011 },
        dynamicTest("answer") { day15Part2(input, 4_000_000) shouldBe 13743542639657 }, // higher than 106047218
    )
}

