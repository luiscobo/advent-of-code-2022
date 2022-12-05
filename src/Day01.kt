fun main() {
    fun part1(input: List<String>): Int {
        var n = 0
        var mayor = 0
        var suma = 0

        input.forEach {
            if (it.trim().isEmpty()) {
                n++
                if (suma > mayor) {
                    mayor = suma
                }
                suma = 0
            }
            else {
                suma += it.toInt()
            }
        }
        n++
        if (suma > mayor) {
            mayor = suma
        }
        return mayor
    }

    fun part2(input: List<String>): Int {
        val caloriasElfos = mutableListOf<Int>()
        var suma = 0

        input.forEach {
            if (it.trim().isEmpty()) {
                caloriasElfos.add(suma)
                suma = 0
            }
            else {
                suma += it.toInt()
            }
        }
        caloriasElfos.add(suma)
        caloriasElfos.sortDescending()
        return caloriasElfos.take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
