import java.lang.StringBuilder

enum class ProgramInstruction(val cycles: Int) {
    NOOP(1),
    ADDX(2)
}

fun parseInstruction(line: String): Pair<ProgramInstruction, Int> {
    val components = line.split(" ")
    if (components[0] == "noop") {
        return ProgramInstruction.NOOP to 0
    }
    val num = components[1].toInt()
    return ProgramInstruction.ADDX to num
}

fun turnOnPixel(crt: MutableList<StringBuilder>, cycle: Int, registerX: Int) {
    val row = (cycle - 1) / 40
    val pixelPos = (cycle - 1) % 40
    if (pixelPos in registerX - 1 .. registerX + 1) {
        crt[row].setCharAt(pixelPos, '#')
    }
}

fun initCRT() =
    MutableList(6) {
        StringBuilder(".".repeat(40))
    }

fun renderCRT(crt: MutableList<StringBuilder>) {
    crt.forEach {
        println(it)
    }
}

const val LAST_CYCLE = 220

fun main() {
    fun part1(input: List<String>): Int {
        var importantCycles = 20
        var sumSignalStrength = 0
        var registerX = 1
        var cycles = 0
        for (line in input) {
            val (instr, num) = parseInstruction(line)
            if ((cycles == importantCycles - 2 && instr == ProgramInstruction.ADDX) ||
                (cycles == importantCycles - 1)){
                if (cycles <= LAST_CYCLE) {
                    sumSignalStrength += registerX * importantCycles
                }
                importantCycles += 40
            }

            registerX += num
            cycles += instr.cycles
        }
        return sumSignalStrength
    }

    fun part2(input: List<String>) {
        val crt = initCRT()
        var registerX = 1
        var cycles = 1
        for (line in input) {
            val (instr, num) = parseInstruction(line)
            repeat (instr.cycles) {
                turnOnPixel(crt, cycles, registerX)
                cycles++
            }
            registerX += num
        }

        renderCRT(crt)
    }

    // ---------------------------------------------

    val input = readInput("day10")
    println(part1(input))
    part2(input)
}