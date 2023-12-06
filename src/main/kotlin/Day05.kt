import kotlinx.collections.immutable.*


@JvmInline
value class Box(val content: Char)

typealias Stack = PersistentList<Box>
typealias Stacks = PersistentMap<Char, Stack>

data class Move(val count: Int, val from: Char, val to: Char)

interface CrateMover {
    val stacks: Stacks
    fun doMove(move: Move): CrateMover

}

data class CrateMover9000(override val stacks: Stacks) : CrateMover {

    override fun doMove(move: Move): CrateMover9000 {
        return if (move.count == 0) this
        else {
            val newFrom = stacks[move.from]!!.dropLast(1)
            val newTo = stacks[move.to]!!.add(stacks[move.from]!!.last())
            this.copy(stacks = stacks.put(move.from, newFrom).put(move.to, newTo))
                .doMove(move.copy(count = move.count - 1))
        }
    }
}

data class CrateMover9001(override val stacks: Stacks) : CrateMover {

    override fun doMove(move: Move): CrateMover9001 {
        val newFrom = stacks[move.from]!!.dropLast(move.count)
        val newTo = stacks[move.to]!!.addAll(stacks[move.from]!!.takeLast(move.count))
        return this.copy(stacks = stacks.put(move.from, newFrom).put(move.to, newTo))
    }

}

fun List<String>.parseStacks(): Stacks {
    val chars = this.reversed().map { line -> line.chunked(4).map { it[1] } }
    val stackIds = chars.first()
    return List(stackIds.size) { i: Int ->
        chars.map { it.getOrNull(i) ?: ' '}
    }.map { it.parseStack() }
        .let {
            persistentMapOf<Char, Stack>().putAll(it)
        }

}

fun List<Char>.parseStack(): Pair<Char, Stack> =
    this.first() to this.drop(1).fold(persistentListOf()) { acc, c ->
        if (c != ' ') acc.add(Box(c))
        else acc
    }

fun List<String>.parseMoves() =
    "move (\\d+) from (.+) to (.+)".toRegex().let {regex ->
        this.map { line ->
            val (count, from, to) = regex.find(line)!!.destructured
            Move(count.toInt(), from[0], to[0])
        }
    }

fun solution(input: List<String>, crateMoverFactory: (Stacks) -> CrateMover): String {
    val stacksPart = input.takeWhile { it.isNotEmpty() }
    val moves: List<Move> = input.drop(stacksPart.size + 1).parseMoves()

    val stacksAfterMoves = moves.fold(crateMoverFactory(stacksPart.parseStacks())) { stacks, move ->
        stacks.doMove(move)
    }.stacks

    return stacksAfterMoves.map { (_, stack) ->
        stack.last().content
    }.joinToString(separator = "")
}

fun day05Part1(input: List<String>): String =
    solution(input) {
        CrateMover9000(it)
    }

fun day05Part2(input: List<String>): String =
    solution(input) {
        CrateMover9001(it)
    }
