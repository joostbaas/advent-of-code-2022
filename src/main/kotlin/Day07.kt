sealed interface FileSystem {
    val name: String

    val size: Int
        get() = when (this) {
            is Directory -> children.sumOf { it.size }
            is File -> fileSize
        }

}

data class File(
    override val name: String,
    val fileSize: Int,
) : FileSystem

data class Directory(
    override val name: String,
    val children: MutableList<FileSystem>,
) : FileSystem


fun Directory.allDirectories(): List<Directory> =
    this.children.filterIsInstance<Directory>().flatMap { it.allDirectories() } + this

fun List<String>.parseFilesystem(): Directory {
    val rootDirectory = Directory("/", mutableListOf())
    val currentDirectory = ArrayDeque<Directory>().apply { add(rootDirectory) }

    val dirRegex = Regex("""dir ([a-z.]+)""")
    val fileRegex = Regex("""([0-9]+) ([a-z.]+)""")
    val cdRegex = Regex("""\$ cd ([a-z]+)""")

    for (line in this.drop(1)) {
        with(line) {
            when {
                matches(dirRegex) -> dirRegex.find(this)!!.destructured
                    .let { (name) ->
                        currentDirectory.last().children.add(Directory(name, mutableListOf()))
                    }

                matches(fileRegex) -> fileRegex.find(this)!!.destructured
                    .let { (size, name) ->
                        currentDirectory.last().children.add(File(name, size.toInt()))
                    }

                equals("$ cd ..") -> currentDirectory.removeLast()
                matches(cdRegex) -> cdRegex.find(this)!!.destructured
                    .let { (name) ->
                        currentDirectory.addLast(
                            currentDirectory.last().children
                                .filterIsInstance<Directory>()
                                .first { it.name == name })
                    }

                else -> { /*doNothing*/
                }
            }
        }
    }

    return rootDirectory
}

fun day07Part1(input: List<String>): Int =
    input.parseFilesystem().allDirectories()
        .filter { it.size < 100_000 }
        .sumOf { it.size }

fun day07Part2(input: List<String>): Int {
    val root = input.parseFilesystem()

    val diskSize = 70_000_000
    val spaceNeeded = 30_000_000
    val spaceAvailable = diskSize - root.size
    val extraSpaceNeeded = spaceNeeded - spaceAvailable

    return root.allDirectories().filter { it.size >= extraSpaceNeeded }.minOf { it.size }
}
