import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.*


fun day01Part1(input: List<String>) = caloriesPerElf(input).max()

fun day01Part2(input: List<String>): Int =
    caloriesPerElf(input).sortedDescending().take(3).sum()

tailrec fun <T> PersistentList<T>.dropLast(n: Int): PersistentList<T> =
    when (n) {
        0 -> this
        else -> this.removeAt(this.size-1).dropLast(n-1)
    }

fun caloriesPerElf(input: List<String>) =
    input.fold(persistentListOf(0)) { acc, line ->
        when (line) {
            "" -> acc.add(0)
            else -> acc.dropLast(1).add(acc.last() + line.toInt())
        }
    }

