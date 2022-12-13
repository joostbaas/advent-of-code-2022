data class Day12(
    private val start: Point,
    private val end: Point,
    private val grid: Map<Point, Char>,
) {

    companion object {
        fun day12Part1(input: List<String>): Int = input.parse().let { it.calculateShortestPathFromStartToEnd() }
        fun day12Part2(input: List<String>): Int = input.parse().let { it.findStartingPointWithShortestPath() }

        private fun List<String>.parse(): Day12 {
            val initial: Triple<Point?, Point?, Map<Point, Char>> = Triple(null, null, emptyMap())
            infix operator fun Triple<Point?, Point?, Map<Point, Char>>.plus(
                other: Triple<Point?, Point?, Map<Point, Char>>,
            ) =
                Triple(
                    first ?: other.first,
                    second ?: other.second,
                    third + other.third,
                )

            val (startingPoint, bestSignalPoint, allPoints) =
                this.foldIndexed(initial) { y: Int, gridAcc, line: String ->
                    val lineResult: Triple<Point?, Point?, Map<Point, Char>> =
                        line.foldIndexed(initial) { x: Int, lineAcc, point: Char ->
                            Point(x, y).let {
                                when (point) {
                                    'S' -> Triple(it, null, mapOf(it to 'a'))
                                    'E' -> Triple(null, it, mapOf(it to 'z'))
                                    else -> Triple(null, null, mapOf(it to point))
                                }
                            } + lineAcc
                        }
                    gridAcc + lineResult
                }

            return Day12(
                startingPoint!!,
                bestSignalPoint!!,
                allPoints
            )
        }
    }

    data class Point(val x: Int, val y: Int)

    private fun Point.candidatesToMoveTo(forbiddenNode: Char?): Set<Point> {
        val thisElevation = grid[this]!!
        return listOf(
            Point(x - 1, y),
            Point(x + 1, y),
            Point(x, y - 1),
            Point(x, y + 1),
        ).filter {
            val elevationOfNeighbour: Char? = grid[it]
            it != this && elevationOfNeighbour != null && elevationOfNeighbour != forbiddenNode && elevationOfNeighbour - thisElevation <= 1
        }.toHashSet()
    }

    fun calculateShortestPathFromStartToEnd(max: Int = grid.size, forbiddenNode: Char? = null): Int {
        var edges: Set<Point> = hashSetOf(start)

        var steps = 1
        while (steps <= max) {
            val accessibleFromPreviousEdges: Set<Point> = edges.flatMap {
                it.candidatesToMoveTo(forbiddenNode)
                    .filterNot { candidate -> edges.contains(candidate) }
            }.toHashSet()
            if (accessibleFromPreviousEdges.contains(end)) return steps
            steps++
            edges = accessibleFromPreviousEdges
        }
        return Int.MAX_VALUE
    }

    fun findStartingPointWithShortestPath(): Int =
        grid.filterValues { elevation -> elevation == 'a' }
            .keys.fold(Int.MAX_VALUE) { minSoFar, start ->
                this.copy(start = start).calculateShortestPathFromStartToEnd(forbiddenNode = 'a').let {
                    minSoFar.coerceAtMost(it)
                }
            }

}

