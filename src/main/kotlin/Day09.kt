import Direction.*
import kotlin.math.abs

enum class Direction {
    UP, DOWN, RIGHT, LEFT
}

fun String.toDirection() =
    when (this) {
        "R" -> RIGHT
        "U" -> UP
        "L" -> LEFT
        "D" -> DOWN
        else -> throw IllegalArgumentException()
    }

fun List<String>.parseRopeMoves() =
    flatMap { line ->
        val (direction, count) = line.split(' ')
        List(count.toInt()) { direction.toDirection() }
    }

data class Point(val x: Int, val y: Int) {

    fun move(direction: Direction) =
        when (direction) {
            UP -> this.copy(y = y + 1)
            DOWN -> this.copy(y = y - 1)
            RIGHT -> this.copy(x = x + 1)
            LEFT -> this.copy(x = x - 1)
        }

    private fun avg(n1: Int, n2: Int) = (n1 + n2) / 2

    private fun x(x: Int) = copy(x = x)
    private fun y(y: Int) = copy(y = y)

    fun moveTowards(other: Point) =
        when (Pair(abs(other.x - this.x), abs(other.y - this.y))) {
            Pair(2, 1) -> other.x(avg(x, other.x))
            Pair(1, 2) -> other.y(avg(y, other.y))
            Pair(0, 2) -> other.y(avg(y, other.y))
            Pair(2, 0) -> other.x(avg(x, other.x))
            Pair(2, 2) -> other.x(avg(x, other.x)).y(avg(y, other.y))
            else -> this
        }
}

data class Situation(val points: List<Point>, val tailVisited: Set<Point> = emptySet()) {

    fun move(direction: Direction): Situation =
        points.fold(emptyList<Point>()) { acc, oldPoint ->
            if (acc.isEmpty()) listOf(oldPoint.move(direction))
            else acc + oldPoint.moveTowards(acc.last())
        }.let { newPoints ->
            Situation(
                newPoints,
                tailVisited = tailVisited + newPoints.last(),
            )
        }
}

fun List<Direction>.countTailPositions(tailCount: Int): Int =
    fold(
        initial = Situation(List(tailCount) { Point(0, 0) })
    ) { situation, direction ->
        situation.move(direction)
    }.tailVisited.size

fun day09Part1(input: List<String>): Int = input.parseRopeMoves().countTailPositions(2)
fun day09Part2(input: List<String>): Int = input.parseRopeMoves().countTailPositions(10)