fun List<String>.parse(): Forest =
    Forest(map { line -> line.map { it.digitToInt() } })

data class Tree(val x: Int, val y: Int) {

    fun treesBetween(other: Tree): List<Tree> {
        require(other.x == x || other.y == y)
        require(other != this)
        return when {
            other.x < x -> (other.x until x).map { Tree(it, y) }.filterNot { it == this || it == other }.reversed()
            other.y < y -> (other.y until y).map { Tree(x, it) }.filterNot { it == this || it == other }.reversed()
            other.x > x -> (x until other.x).map { Tree(it, y) }.filterNot { it == this || it == other }
            other.y > y -> (y until other.y).map { Tree(x, it) }.filterNot { it == this || it == other }
            else -> throw IllegalArgumentException()
        }
    }

}

class Forest(private val grid: List<List<Int>>) {

    private val horizontalSize = grid[0].size
    private val verticalSize = grid.size

    private val allTrees = (0 until horizontalSize).flatMap { xc ->
        (0 until verticalSize).map { yc ->
            Tree(xc, yc)
        }
    }

    fun countVisibleFromOutside(): Int {
        return allTrees.count { it.isVisibleFromOutside() }
    }

    fun List<List<Int>>.get(tree: Tree) = this[tree.y][tree.x]

    private fun Tree.seesOther(other: Tree): Boolean =
        grid.get(this).let { thisTreeSize ->
            treesBetween(other).all {
                val treeInBetweenSize = grid.get(it)
                treeInBetweenSize < thisTreeSize
            }
        }


    private fun Tree.neighboursBottom() = (y + 1 until verticalSize).map { Tree(x, it) }
    private fun Tree.neighboursTop() = (0 until y).map { Tree(x, it) }
    private fun Tree.neighboursRight() = (x + 1 until horizontalSize).map { Tree(it, y) }
    private fun Tree.neighboursLeft() = (0 until x).map { Tree(it, y) }

    private fun Tree.scenicScore(): Int =
        listOf(
            neighboursTop(),
            neighboursLeft(),
            neighboursRight(),
            neighboursBottom(),
        ).map { neighbours -> neighbours.count { otherTree -> seesOther(otherTree) } }
            .fold(1) { acc, count -> count * acc }

    fun highestScenicScore() = allTrees.maxOf { it.scenicScore() }

    private fun Tree.isVisibleFromOutside(): Boolean {
        if (x == 0 || y == 0 || x == horizontalSize - 1 || y == verticalSize - 1) return true
        val sizeOfTree = grid.get(this)
        return listOf(
            neighboursLeft(),
            neighboursRight(),
            neighboursTop(),
            neighboursBottom(),
        ).any { neighbours -> neighbours.all { grid.get(it) < sizeOfTree } }
    }


}

fun day08Part1(input: List<String>): Int = input.parse().countVisibleFromOutside()
fun day08Part2(input: List<String>): Int = input.parse().highestScenicScore()