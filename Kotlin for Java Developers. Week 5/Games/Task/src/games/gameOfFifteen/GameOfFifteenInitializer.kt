package games.gameOfFifteen

import java.lang.IllegalArgumentException

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

fun <T: Comparable<T>> List<T>.findUnorderedIndexes():Pair<Int, Int> {
    for (i in 0 until size - 1) {
        for (j in i + 1 until size - 1) {
            if (get(i) > get(j)) return i to j
        }
    }
    throw IllegalArgumentException("not unordered")
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val tryInitial = (1..15).shuffled().toMutableList()
        if (!isEven(tryInitial)) {
            val (i, j) = tryInitial.findUnorderedIndexes()
            tryInitial.swap(i, j)
        }
        if (!isEven(tryInitial)) {
            throw IllegalArgumentException("impossible to copute initial permutation")
        }
        tryInitial
    }
}

