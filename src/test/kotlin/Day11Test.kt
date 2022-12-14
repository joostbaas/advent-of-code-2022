import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.*
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test

internal class Day11Test {

    private val testMonkeys =
        listOf(
            Monkey(
                itemIds = listOf(79, 98),
                operation = { Item(it.worryValue * 19) },
                test = Monkey.Test(23, monkey(2), monkey(3))
            ),
            Monkey(
                itemIds = listOf(54, 65, 75, 74),
                operation = { Item(it.worryValue + 6) },
                test = Monkey.Test(19, monkey(2), monkey(0))
            ),
            Monkey(
                itemIds = listOf(79, 60, 97),
                operation = { Item(it.worryValue * it.worryValue) },
                test = Monkey.Test(13, monkey(1), monkey(3))
            ),
            Monkey(
                itemIds = listOf(74),
                operation = { Item(it.worryValue + 3) },
                test = Monkey.Test(17, monkey(0), monkey(1))
            ),
        )

    @Test
    fun createMonkey() {
        val testMonkey0 = testMonkeys[0].copy()

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
                day11Part1(realMonkeys) shouldBe 98280
            }
        )


    companion object {
        private const val testDivisor = 23 * 19 * 13 * 17
    }

    @TestFactory
    fun `part 2`() =
        listOf(
            dynamicTest("test part 2") {
                day11Part2(testMonkeys, testDivisor) shouldBe 2713310158
            },
            dynamicTest("answer part 2") {
                day11Part2(realMonkeys, realDivisor) shouldBe 17673687232
            }
        )
}


