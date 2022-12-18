enum class FileType {
    PLAIN_FILE,
    DIRECTORY
}

data class Node(val fileName: String, val fileType: FileType, var size: Int, var parent: Node? = null) {
    private val children: MutableList<Node> = mutableListOf()

    fun getChildByName(dirName: String): Node? {
        return children.firstOrNull {
            it.fileName == dirName
        }
    }

    fun getChild(position: Int) = children[position]

    operator fun get(i: Int) = children[i]

    val indices: IntRange
        get() = IntRange(0, children.size - 1)

    val isDir get() = this.fileType == FileType.DIRECTORY

    fun addChild(node: Node) {
        children.add(node)
    }

    val length: Int
        get() = children.size
}

// La estructura de directorios
var root: Node? = null
const val TOTAL_DISK_SPACE_AVAILABLE = 70_000_000
const val UNUSED_SPACE_NEEDED = 30_000_000

fun calculateTotalSize(node: Node): Int {
    var total = 0
    for (i in node.indices) {
        val n = node[i]
        total += if (n.fileType == FileType.PLAIN_FILE) {
            n.size
        }
        else {
            calculateTotalSize(n)
        }
    }
    node.size = total
    return total
}

fun printNode(node: Node, spaces: String = "") {
    println("$spaces ${node.fileName} : ${node.size}")
    if (node.fileType == FileType.DIRECTORY) {
        for (i in node.indices) {
            val child = node[i]
            if (child.fileType == FileType.DIRECTORY) {
                printNode(child, "$spaces  ")
            }
        }
    }
}


fun main() {
    fun getCommandParts(line: String): Pair<Boolean, String> {
        val arguments = line.split(" ")
        return if (arguments[1] == "ls") {
            false to ""
        }
        else if (arguments[1] == "cd") {
            true to arguments[2]
        }
        else {
            false to "ERROR"
        }
    }

    fun getFileInfo(line: String): Node {
        val arguments = line.split(" ")
        if (arguments[0] == "dir") {
            return Node(arguments[1], FileType.DIRECTORY, 0)
        }
        return Node(arguments[1], FileType.PLAIN_FILE, arguments[0].toInt())
    }

    fun getTotalSizeDirectoriesAtMost100K(node: Node): Int {
        var total = 0
        if (node.fileType == FileType.DIRECTORY) {
            if (node.size <= 100_000) {
                total += node.size
            }
            for (i in node.indices) {
                val child = node[i]
                if (child.isDir) {
                    total += getTotalSizeDirectoriesAtMost100K(child)
                }
            }
        }
        return total
    }

    fun part1(input: List<String>): Int {
        var workingDir: Node? = null

        for (command in input) {
            if (command[0] == '$') {
                val (isCD, directory) = getCommandParts(command)
                if (isCD) {
                    when (directory) {
                        "/" -> {
                            if (root == null) {
                                root = Node("/", FileType.DIRECTORY, 0)
                            }
                            workingDir = root!!
                        }
                        ".." -> workingDir = workingDir!!.parent
                        else -> workingDir = workingDir?.getChildByName(directory)
                    }
                }
            }
            else {
                val node = getFileInfo(command)
                node.parent = workingDir
                workingDir!!.addChild(node)
            }
        }
        calculateTotalSize(root!!)
        return getTotalSizeDirectoriesAtMost100K(root!!)
    }

    fun totalSizeDirectoryToDelete(node: Node, needed: Int): Int {
        var menor = 0
        if (node.isDir) {
            menor = if (node.size >= needed) node.size else Int.MAX_VALUE
            for (i in node.indices) {
                val child = node[i]
                if (child.isDir) {
                    val total = totalSizeDirectoryToDelete(child, needed)
                    if (total in needed until menor) {
                        menor = total
                    }
                }
            }
        }
        return menor
    }

    fun part2(input: List<String>): Int {
        root = null
        part1(input)

        val unused = TOTAL_DISK_SPACE_AVAILABLE - root!!.size
        if (unused >= UNUSED_SPACE_NEEDED) {
            return 0
        }
        val needed = UNUSED_SPACE_NEEDED - unused
        println("Needed $needed")
        return totalSizeDirectoryToDelete(root!!, needed)
    }

    val input = readInput("day07")
    println(part1(input))

    println(part2(input))
}