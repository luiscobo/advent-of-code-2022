fun main() {
    var rows: MutableList<IntArray> = mutableListOf()

    fun addRow(line: String, index: Int) {
        val numbers = IntArray(line.length) { pos: Int -> line[pos].digitToInt() }
        rows.add(numbers)
    }

    fun visibleFromTop(row: Int, col: Int): Boolean {
        val value = rows[row][col]
        for (i in 0 until row) {
            if (rows[i][col] >= value) {
                return false
            }
        }
        return true
    }

    fun visibleFromBottom(row: Int, col: Int): Boolean {
        val value = rows[row][col]
        for (i in row + 1 until rows.size) {
            if (rows[i][col] >= value) {
                return false
            }
        }
        return true
    }

    fun visibleFromLeft(row: Int, col: Int): Boolean {
        val value = rows[row][col]
        for (i in 0 until col) {
            if (rows[row][i] >= value) {
                return false
            }
        }
        return true
    }

    fun visibleFromRight(row: Int, col: Int): Boolean {
        val value = rows[row][col]
        for (i in col + 1 until rows.size) {
            if (rows[row][i] >= value) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        rows = mutableListOf()
        input.forEachIndexed { index: Int, s: String ->
            addRow(s, index)
        }

        var result = rows[0].size * 2 + 2 * (rows.size - 2)
        for (i in 1 until rows.size - 1) {
            for (j in 1 until rows.size - 1) {
                if (visibleFromBottom(i, j) || visibleFromTop(i, j) || visibleFromLeft(i, j) || visibleFromRight(i, j)) {
                    result++
                }
            }
        }
        return result
    }

    fun viewingDistanceUp(row: Int, col: Int): Int {
        var i = row - 1
        val value = rows[row][col]
        var res = 0
        while (i >= 0) {
            res++
            if (rows[i][col] >= value) {
                break
            }
            i--
        }
        return res
    }

    fun viewingDistanceDown(row: Int, col: Int): Int {
        var i = row + 1
        val value = rows[row][col]
        var res = 0
        while (i < rows.size) {
            res++
            if (rows[i][col] >= value) {
                break
            }

            i++
        }
        return res
    }

    fun viewingDistanceLeft(row: Int, col: Int): Int {
        var j = col - 1
        val value = rows[row][col]
        var res = 0
        while (j >= 0) {
            res++
            if (rows[row][j] >= value) {
                break
            }
            j--
        }
        return res
    }

    fun viewingDistanceRight(row: Int, col: Int): Int {
        var j = col + 1
        val value = rows[row][col]
        var res = 0
        while (j < rows.size) {
            res++
            if (rows[row][j] >= value) {
                break
            }
            j++
        }
        return res
    }

    fun scenicScore(row: Int, col: Int): Int =
        viewingDistanceDown(row, col) *
                viewingDistanceUp(row, col) *
                viewingDistanceLeft(row, col) *
                viewingDistanceRight(row, col)

    fun part2(input: List<String>): Int {
        rows = mutableListOf()
        input.forEachIndexed { i, s ->
            addRow(s, i)
        }
        var mayor = 0
        for (i in rows.indices) {
            for (j in rows.indices) {
                val sc = scenicScore(i, j)
                if (sc > mayor) {
                    mayor = sc
                }
            }
        }
        return mayor
    }

    //------------------------------------------------
    val input = readInput("day08")
    println(part1(input))
    println(part2(input))
}