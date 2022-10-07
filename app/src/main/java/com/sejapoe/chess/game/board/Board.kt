package com.sejapoe.chess.game.board

import android.app.Activity
import com.sejapoe.chess.game.CellState
import com.sejapoe.chess.game.PieceColor
import com.sejapoe.chess.game.pieces.*

class Board(activity: Activity) {
    // Initialize cells, assign for each cell it's imageView
    val cells: MutableList<MutableList<Cell>> = MutableList(8) {
        MutableList(8) { jt ->
            val textId = "${'a' + jt}${it + 1}"
            val id = activity.resources.getIdentifier(textId, "id", activity.packageName)
            Cell(activity.findViewById(id), textId)
        }
    }
    private var selectedCell: Cell? = null
        set(value) {
            resetSelection()
            if (value != null) {
                value.state = CellState.STAY
                value.piece!!.selectAvailableCells(value.row, value.column, this)
            }
            field = value
        }

    // Add interaction logic
    init {
        for (cell in cells.flatten()) {
            cell.setOnClickListener {
                if (!tryMoveSelectedTo(it) && it.piece != null) {
                    selectedCell = it
                }
            }
        }
    }

    // Set all cells to initial state
    fun resetSetup() {
        resetSelection()
        cells.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                cell.piece = getDefaultPieceFor(i, j)
            }
        }
    }

    private fun tryMoveSelectedTo(destinationCell: Cell): Boolean {
        val fixedSelectedCell = selectedCell ?: return false

        if (destinationCell.piece != null && destinationCell.piece!!.getColor() == fixedSelectedCell.piece!!.getColor()) return false // todo: attack

        when (destinationCell.state) {
            CellState.MOVE -> move(fixedSelectedCell, destinationCell)
            CellState.ATTACK -> TODO()
            CellState.CAST -> cast(fixedSelectedCell, destinationCell)
            else -> {}
        }
        selectedCell = null
        return true
    }

    private fun cast(kingCell: Cell, destinationCell: Cell) {
        val delta = if (kingCell.column > destinationCell.column) 1 else -1
        move(kingCell, destinationCell)
        move(
            cells[destinationCell.row][destinationCell.column - delta],
            cells[destinationCell.row][destinationCell.column + delta]
        )
    }

    private fun move(sourceCell: Cell, destinationCell: Cell) {
        destinationCell.piece = when (sourceCell.piece) {
            is CastingParticipant -> {
                (sourceCell.piece as CastingParticipant).wasMoved = true
                sourceCell.piece
            }

            is Pawn -> if (destinationCell.row == if (sourceCell.piece!!.getColor() == PieceColor.WHITE) 7 else 0) {
                Queen(sourceCell.piece!!.getColor())
            } else {
                sourceCell.piece
            }

            else -> sourceCell.piece
        }
        sourceCell.piece = null
    }

    private fun resetSelection() {
        forEach {
            it.state = CellState.NONE
        }
    }

    fun forEach(foo: (value: Cell) -> Unit) {
        for (r in 0..7) {
            for (c in 0..7) {
                foo(cells[r][c])
            }
        }
    }

    companion object {
        fun getDefaultPieceFor(r: Int, c: Int): Piece? {
            val color = if (r > 4) PieceColor.BLACK else PieceColor.WHITE
            return when (r) {
                0, 7 -> when (c) {
                    0, 7 -> Rook(color)
                    1, 6 -> Knight(color)
                    2, 5 -> Bishop(color)
                    3 -> Queen(color)
                    4 -> King(color)
                    else -> null
                }

                1, 6 -> Pawn(color, r)
                else -> null
            }
        }
    }
}