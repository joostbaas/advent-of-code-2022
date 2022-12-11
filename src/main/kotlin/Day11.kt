class Monkeys(val monkeys: List<Monkey>) {

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
value class Item(val worryValue: Int)

class Monkey(
    val items: MutableList<Item> = mutableListOf(),
    val test: Test,
    val operation: (Item) -> Int,
) {

    constructor(itemIds: List<Int>, operation: (Item) -> Int, test: Test) :
            this(itemIds.map { Item(it) }.toMutableList(), test, operation)

    var itemsInspected = 0
        private set(value) {
            field = value
        }

    fun turn() =
        items.map {
            itemsInspected = itemsInspected + 1
            operation(it)
        }
            .map { Item(it / 3) }
            .map { item ->
                `throw`(item to test(item))
            }

    private fun `throw`(itemToMonkey: Pair<Item, MonkeyId>) = itemToMonkey
}

class Test(
    private val divisor: Int,
    private val whenTrue: MonkeyId,
    private val whenFalse: MonkeyId,
) : (Item) -> MonkeyId {
    override fun invoke(item: Item): MonkeyId =
        if (item.worryValue % divisor == 0) whenTrue else whenFalse
}

fun day11Part1(monkeys: List<Monkey>): Int =
    Monkeys(monkeys).let {
        it.doRounds(20)
        val (m1, m2) = monkeys.map { it.itemsInspected }
            .sortedDescending()
            .take(2)
        m1 * m2
}
fun day11Part2(input: List<String>): Int = TODO()