import Monkey.Test

val realMonkeys: List<Monkey>
    get() =
        listOf(
            Monkey(
                itemIds = listOf(56, 52, 58, 96, 70, 75, 72),
                operation = { Item(it.worryValue * 17) },
                test = Test(11, monkey(2), monkey(3))
            ),
            Monkey(
                itemIds = listOf(75, 58, 86, 80, 55, 81),
                operation = { Item(it.worryValue + 7) },
                test = Test(3, monkey(6), monkey(5))
            ),
            Monkey(
                itemIds = listOf(73, 68, 73, 90),
                operation = { Item(it.worryValue * it.worryValue) },
                test = Test(5, monkey(1), monkey(7))
            ),
            Monkey(
                itemIds = listOf(72, 89, 55, 51, 59),
                operation = { Item(it.worryValue + 1) },
                test = Test(7, monkey(2), monkey(7))
            ),
            Monkey(
                itemIds = listOf(76, 76, 91),
                operation = { Item(it.worryValue * 3) },
                test = Test(19, monkey(0), monkey(3))
            ),
            Monkey(
                itemIds = listOf(88),
                operation = { Item(it.worryValue + 4) },
                test = Test(2, monkey(6), monkey(4))
            ),
            Monkey(
                itemIds = listOf(64, 63, 56, 50, 77, 55, 55, 86),
                operation = { Item(it.worryValue + 8) },
                test = Test(13, monkey(4), monkey(0))
            ),
            Monkey(
                itemIds = listOf(79, 58),
                operation = { Item(it.worryValue + 6) },
                test = Test(17, monkey(1), monkey(5))
            ),
        ).map { monkey -> monkey.copy(items = mutableListOf<Item>().apply { addAll(monkey.items) } )  }


class Monkeys(private val monkeys: List<Monkey>) {

    fun doRounds(count: Int) {
        repeat(count) {
            monkeys.forEach { monkey ->
                val thrownItems = monkey.turn()
                monkey.items.clear()
                thrownItems.forEach { (item, monkeyId) ->
                    monkeys[monkeyId.id].items.add(item)
                }
            }
        }
    }

}

@JvmInline
value class MonkeyId(val id: Int)

fun monkey(id: Int) = MonkeyId(id)

@JvmInline
value class Item(val worryValue: Long)

fun Monkey(
    itemIds: List<Int>,
    operation: (Item) -> Item,
    test: Test,
): Monkey {
    return Monkey.create(itemIds, operation, test)
}

data class Monkey(
    val items: MutableList<Item> = mutableListOf(),
    val test: Test,
    val operation: (Item) -> Item,
    val worryLevelDecrease: (Item) -> Item = { Item(it.worryValue / 3) },
) {

    companion object {
        fun create(
            itemIds: List<Int>,
            operation: (Item) -> Item,
            test: Test,
        ): Monkey {
            return Monkey(itemIds.map { Item(it.toLong()) }.toMutableList(), test, operation).copy()
        }
    }

    var itemsInspected = 0L
        private set

    fun turn() =
        items.map {
            itemsInspected += 1
            operation(it)
        }
            .map(worryLevelDecrease)
            .map { item ->
                `throw`(item to test(item))
            }

    private fun `throw`(itemToMonkey: Pair<Item, MonkeyId>) = itemToMonkey

    class Test(
        private val divisor: Int,
        private val whenTrue: MonkeyId,
        private val whenFalse: MonkeyId,
    ) : (Item) -> MonkeyId {
        override fun invoke(item: Item): MonkeyId =
            if (item.worryValue % divisor == 0L) whenTrue else whenFalse
    }
}

private fun calculateMonkeyBusiness(monkeys: List<Monkey>, numberOfRounds: Int) =
    Monkeys(monkeys).let {
        it.doRounds(numberOfRounds)
        val (m1, m2) = monkeys.map { monkey -> monkey.itemsInspected }
            .sortedDescending()
            .take(2)
        m1 * m2
    }

fun day11Part1(monkeys: List<Monkey>): Long =
    calculateMonkeyBusiness(monkeys, 20)

const val realDivisor = 11 * 3 * 5 * 7 * 19 * 2 * 13 * 17 // the monkey's divisor from their tests
fun day11Part2(monkeys: List<Monkey>, divisor: Int): Long =
    calculateMonkeyBusiness(
        monkeys.map { monkey ->
            monkey.copy(worryLevelDecrease = { Item(it.worryValue % divisor) })
        },
        10_000
    )