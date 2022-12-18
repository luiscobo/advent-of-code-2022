import java.util.*

fun main() {
    val stacks = mutableListOf<Stack<Char>>()

    fun crearPilas(n: Int) {
        for (i in 0 until n) {
            stacks.add(Stack())
        }
    }

    fun procesarLinea(linea: String): Boolean {
        if (linea.isNotEmpty()) {
            for (i in 1 until linea.length step 4) {
                if (linea[i].isUpperCase()) {
                    val n = (i - 1) / 4
                    stacks[n].push(linea[i])
                }
            }
            return true
        }
        return false
    }

    fun procesarPilas(input: List<String>): Int {
        var n = 0
        while (!input[n][1].isDigit()) {
            n++
        }
        val aux = input[n].split(" ")
        var x = aux.size - 1
        var tam = 0
        while (x >= 0) {
            if (aux[x].isNotEmpty()) {
                tam = aux[x].toInt()
                break
            }
            x--
        }
        crearPilas(tam)
        for (i in n - 1 downTo 0) {
            val linea = input[i]
            procesarLinea(linea)
        }
        return n
    }

    fun obtenerDatosLinea(linea: String): Triple<Int, Int, Int> {
        val lista = linea.split(" ")
        return Triple(lista[1].toInt(), lista[3].toInt() - 1, lista[5].toInt() - 1)
    }

    fun ejecutarMovimiento(cuantos: Int, desde: Int, hasta: Int) {
        repeat (cuantos) {
            val elem = stacks[desde].pop()
            stacks[hasta].push(elem)
        }
    }

    // -------------------------------------------------------

    fun part1(input: List<String>): String {
        val numPilas = procesarPilas(input)

        for (i in numPilas + 2 until input.size) {
            val linea = input[i]
            val (cuantos, desde, hasta) = obtenerDatosLinea(linea)
            ejecutarMovimiento(cuantos, desde, hasta)
        }

        var resp = ""
        for (stack in stacks) {
            resp += stack.peek()
        }
        println(stacks[0])
        return resp
    }

    // -------------------------------------------------------------

    fun ejercutarMovimiento9001(cuantos: Int, desde: Int, hasta: Int) {
        val posInicial = stacks[desde].size - cuantos
        repeat (cuantos) {
            val elem = stacks[desde].get(posInicial)
            stacks[hasta].push(elem)
            stacks[desde].removeAt(posInicial)
        }
    }

    fun part2(input: List<String>): String {
        stacks.clear()
        val numPilas = procesarPilas(input)

        for (i in numPilas + 2 until input.size) {
            val linea = input[i]
            val (cuantos, desde, hasta) = obtenerDatosLinea(linea)
            ejercutarMovimiento9001(cuantos, desde, hasta)
        }

        var resp = ""
        for (stack in stacks) {
            resp += stack.peek()
        }
        return resp
    }

    // -------------------------------------------------------------

    val input = readInput("day05")
    println(part1(input))
    println(part2(input))
}