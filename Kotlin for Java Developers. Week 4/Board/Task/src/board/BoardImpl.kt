package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = object: SquareBoard {
    private val cells: MutableMap<Pair<Int, Int>, Cell> = mutableMapOf()
    override val width: Int
        get() = width

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i < 1 || i > width) return null
        if (j < 1 || j > width) return null
        cells.putIfAbsent(i to j, Cell(i, j))
        return cells[i to j]
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return (1..width).flatMap { getRow(it, IntProgression.fromClosedRange(1, width, 1))}
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.mapNotNull { getCellOrNull(i, it) }

    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.mapNotNull { getCellOrNull(it, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction) {
            UP -> getCellOrNull(i - 1, j)
            DOWN -> getCellOrNull(i +1, j)
            LEFT -> getCellOrNull(i, j - 1)
            RIGHT -> getCellOrNull(i, j + 1)
        }
    }

}

class GameBoardImpl <T>(private val squareBoard: SquareBoard): GameBoard<T>, SquareBoard by squareBoard {
    private val cells: MutableMap<Cell, T?> = mutableMapOf()

    init {
        (1..width).flatMap { i -> (1..width).map { j -> i to j} }.forEach {(i, j) ->
            val cell = getCell(i, j)
            cells[cell] = null
        }
    }

    override fun get(cell: Cell): T? {
        return cells[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cells[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cells.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return filter(predicate).firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cells.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cells.values.all(predicate)
    }
}
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(createSquareBoard(width))

