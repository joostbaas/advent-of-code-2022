import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class Day06Test {

    private val input = readInput("Day06")[0]

    @TestFactory
    fun `part 1`() =
        listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 7,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
            "nppdvjthqldpwncqszvftbrmjlhg" to 6,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11,
            input to 1042
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(input.take(5) + "...") {
                day06Part1(input) shouldBe expected
            }
        }


    @TestFactory
    fun `part 2`() =
        listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
            "nppdvjthqldpwncqszvftbrmjlhg" to 23,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26,
            input to 2980,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest(input.take(5) + "...") {
                day06Part2(input) shouldBe expected
            }
        }
}
