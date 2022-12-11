import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.*
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test

internal class Day11Test {

    private val testInput = readInput("Day11_test")
    private val input = readInput("Day11")

    private val testMonkeys = listOf(
        Monkey(
            itemIds = listOf(79, 98),
            operation = { it.worryValue * 19 },
            test = Test(23, monkey(2), monkey(3))
        ),
        Monkey(
            itemIds = listOf(54, 65, 75, 74),
            operation = { it.worryValue + 6 },
            test = Test(19, monkey(2), monkey(0))
        ),
        Monkey(
            itemIds = listOf(79, 60, 97),
            operation = { it.worryValue * it.worryValue },
            test = Test(13, monkey(1), monkey(3))
        ),
        Monkey(
            itemIds = listOf(74),
            operation = { it.worryValue + 3 },
            test = Test(17, monkey(0), monkey(1))
        ),
    )

    @Test
    fun createMonkey() {
        val testMonkey0 = testMonkeys[0]

        val itemsThrown = testMonkey0.turn()

        itemsThrown shouldBe
                listOf(
                    Item(500) to monkey(3),
                    Item(620) to monkey(3),
                )
    }

    @Test
    fun doRound() {
        Monkeys(testMonkeys).doRounds(1)

        testMonkeys.map {
            it.items.map { item -> item.worryValue }
        } shouldBe listOf(
            listOf(20, 23, 27, 26),
            listOf(2080, 25, 167, 207, 401, 1046),
            listOf(),
            listOf(),
        )
    }

    @Test
    fun doTwentyRounds() {
        Monkeys(testMonkeys).doRounds(20)

        testMonkeys.map {
            it.items.map { item -> item.worryValue }
        } shouldBe listOf(
            listOf(10, 12, 14, 26, 34),
            listOf(245, 93, 53, 199, 115),
            listOf(),
            listOf(),
        )
    }


    @TestFactory
    fun `part 1`() =
        listOf(
            dynamicTest("test part 1") {
                day11Part1(testMonkeys) shouldBe 10605
            },
            dynamicTest("answer part 1") {
                day11Part1(testMonkeys) shouldBe TODO()
            }
        )


    @TestFactory
    fun `part 2`() =
        listOf(
            dynamicTest("test part 2") {
                day11Part2(testInput) shouldBe 10605
            },
            dynamicTest("answer part 2") {
                day11Part2(input) shouldBe TODO()
            }
        )
}


