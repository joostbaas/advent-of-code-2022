import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class Day10Test {

    private val testInput = readInput("Day10_test")
    private val input = readInput("Day10")

    @Test
    fun `part 1`() {
        day10Part1(testInput) shouldBe 13140
        day10Part1(input) shouldBe 11780
    }

    @Test
    fun `part 2`() {
        day10Part2(testInput) shouldBe """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
        day10Part2(input) shouldBe """
            ###..####.#..#.#....###...##..#..#..##..
            #..#....#.#..#.#....#..#.#..#.#..#.#..#.
            #..#...#..#..#.#....###..#..#.#..#.#..#.
            ###...#...#..#.#....#..#.####.#..#.####.
            #....#....#..#.#....#..#.#..#.#..#.#..#.
            #....####..##..####.###..#..#..##..#..#.
        """.trimIndent()
    }
}
