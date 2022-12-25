import javax.swing.plaf.metal.MetalTabbedPaneUI
import kotlin.math.abs
import kotlin.math.sign

typealias Position = Pair<Int, Int>
// Para saber si las posiciones están cercanas
fun touching(H: Position, T: Position): Boolean {
    return abs(H.first - T.first) <= 1 &&
            abs(H.second - T.second) <= 1
}

fun signo(x: Int) =
    when {
        x < 0 -> -1
        x == 0 -> 0
        else -> 1
    }

fun moveTail(H: Position, T: Position): Position {
    var (xh, yh) = H
    var (xt, yt) = T

    return (xt + signo(xh - xt)) to (yt + signo(yh - yt))
}

fun getComponents(line: String): Pair<Char, Int> {
    return line[0] to line.substringAfter(' ').toInt()
}

fun move(H: Position, direction: Char): Position {
    return (H.first + when (direction) {
        'R' -> 1
        'L' -> -1
        else -> 0
    }) to (H.second + when (direction) {
        'U' -> 1
        'D' -> -1
        else -> 0
    })
}

fun moveFirst(knots: MutableList<Position>, direction: Char) {
    knots[0] = move(knots[0], direction)
}

fun moveRest(knots: MutableList<Position>) {
    for (i in 1 .. 9) {
        if (!touching(knots[i-1], knots[i])) {
            knots[i] = moveTail(knots[i - 1], knots[i])
        }
        else {
            break
        }
    }
}


fun clone(position: Position): Position = position.first to position.second
fun main() {
    fun part1(input: List<String>): Int {
        var H: Position = 0 to 0
        var T: Position = 0 to 0
        val positions: MutableSet<Position> = mutableSetOf(clone(T))

        for (line in input) {
            var (dir, args) = getComponents(line)
            repeat (args) {
                H = move(H, dir)
                if (!touching(H, T)) {
                    T = moveTail(H, T)
                    positions.add(clone(T))
                }
            }
        }
        return positions.size
    }

    fun part2(input: List<String>): Int {
        val positions: MutableSet<Position> = mutableSetOf(0 to 0)
        val knots: MutableList<Position> = mutableListOf()
        for (i in 0 until 10) {
            knots.add(Pair(0, 0))
        }

        for (line in input) {
            var (dir, args) = getComponents(line)
            repeat(args) {
                moveFirst(knots, dir)
                moveRest(knots)
                positions.add(clone(knots.last()))
            }
        }
        return positions.size

    }

    //-----------------------------------------------------

    val input = readInput("day09")
    println(part1(input))
    println(part2(input))
}


