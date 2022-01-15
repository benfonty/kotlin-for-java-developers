package games.gameOfFifteen

import java.lang.IllegalArgumentException

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val shift = if (0 in permutation) 0 else 1
    val workList = permutation.toMutableList()
    var nbPermutations = 0
    for (i in workList.size-1 downTo 0) {
        val elementThatShouldBeThere = i + shift
        if (workList[i] != elementThatShouldBeThere) {
            val index = workList.indexOf(elementThatShouldBeThere)
                .takeIf { it != -1 }
                ?: throw IllegalArgumentException("missing element in permutation")

            workList.swap(i, index)
            nbPermutations += 1
        }
    }
    return nbPermutations % 2 == 0
}

fun <T> MutableList<T>.swap(i: Int, j:Int) {
    val tmp = get(i)
    set(i, get(j))
    set(j, tmp)
}