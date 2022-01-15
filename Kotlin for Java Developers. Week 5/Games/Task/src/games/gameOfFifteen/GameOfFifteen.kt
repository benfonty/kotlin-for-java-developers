package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import java.lang.IllegalArgumentException
import board.Direction.*


fun Direction.opposite() = when(this) {
    UP -> DOWN
    DOWN -> UP
    LEFT -> RIGHT
    RIGHT -> LEFT
}
/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    object : Game {
        private val board = createGameBoard<Int?>(4)

        override fun initialize() {
            board.getAllCells().zip(initializer.initialPermutation).forEach {
                board[it.first] = it.second
            }
        }

        override fun canMove(): Boolean {
            return true
        }

        override fun hasWon(): Boolean {
            val target:MutableList<Int?> = (1..15).toMutableList()
            target.add(null)
            return board.getAllCells().map { board[it] } == target
        }

        override fun processMove(direction: Direction) {
            with(board) {
                val emptyCell = find { it == null }
                    .takeIf { it != null }
                    ?: throw IllegalArgumentException("not able to find an empty cell")
                emptyCell
                    .getNeighbour(direction.opposite())
                    ?.let {
                        set(emptyCell, get(it))
                        set(it, null)
                    }
            }
        }

        override fun get(i: Int, j: Int): Int? {
            return board.run { get(getCell(i, j)) }
        }

    }

