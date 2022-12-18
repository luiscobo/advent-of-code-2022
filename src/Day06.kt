fun main() {
    fun todosDiferentes(buffer: String, n: Int, cuantos: Int): Boolean {
        if (n < buffer.length) {
            val seccion = buffer.substring(n - cuantos + 1 .. n)
            return seccion.toCharArray().distinct().size == cuantos
        }
        return false
    }


    fun part1(input: String): Int {
        for (i in 3 until input.length) {
            if (todosDiferentes(input, i, 4)) {
                return i + 1
            }
        }
        return -1
    }

    fun part2(input: String): Int {
        for (i in 13 until input.length) {
            if (todosDiferentes(input, i, 14)) {
                return i + 1
            }
        }
        return -1
    }

    val input = readInput("day06_input")
    println(part1(input[0]))
    println(part2(input[0]))
}