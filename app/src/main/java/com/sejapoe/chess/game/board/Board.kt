package com.sejapoe.chess.game.board

import android.app.Activity
import com.sejapoe.chess.game.Game
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.piece.*
import com.sejapoe.chess.game.piece.core.CastingParticipant
import com.sejapoe.chess.game.piece.core.PieceColor
import com.sejapoe.chess.game.theme.Theme

class Board(activity: Activity, val theme: Theme, val game: Game) : IBoard {
    // Initialize cells, assign for each cell it's imageView
    override val cells: MutableList<MutableList<ICell>> = MutableList(8) {
        MutableList(8) { jt ->
            val textId = "${'a' + jt}${it + 1}"
            val id = activity.resources.getIdentifier(textId, "id", activity.packageName)
            Cell(activity.findViewById(id), this, textId)
        }
    }

    override var state: BoardState = BoardState.DEFAULT

    var selectedCell: Cell? = null
        set(value) {
            forEach {
                it.state = CellState.NONE
            }
            if (value != null) {
                value.state = CellState.STAY
                value.selectPossibleTurns()
            }
            field = value
        }


    // Set all cells to initial state
    fun resetSetup() {
        selectedCell = null // Reset selection
        cells.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                cell.piece = getDefaultPieceFor(i, j, theme)
            }
        }
        cells.flatten().forEach { it.updatePossibleTurns() }
    }

    private fun performTurn() {
        this.state = BoardState.DEFAULT
        cells.flatten().forEach { it.resetPossibleTurns() }
        cells.flatten().filter { it.piece?.color == game.turn }.forEach {
            it.updatePossibleTurns()
        }
        this.checkForCheck()
        var flag = true
        cells.flatten().filter { it.piece?.color == !game.turn }.forEach {
            it.updatePossibleTurns()
            if (flag && !it.possibleTurns.flatten().all { jt -> jt == CellState.NONE || jt == CellState.STAY }) {
                flag = false
            }
        }
        if (flag) {
            this.state = BoardState.CHECKMATE
        }
        game.turn = !game.turn
    }

    fun tryMoveSelectedTo(destinationCell: Cell): Boolean {
        val fixedSelectedCell = selectedCell ?: return false

        when (destinationCell.state) {
            CellState.MOVE -> move(fixedSelectedCell, destinationCell)
            CellState.ATTACK -> move(fixedSelectedCell, destinationCell) // TODO: Make distinct attack logic
            CellState.CAST -> cast(fixedSelectedCell, destinationCell)
            else -> {
                selectedCell = null
                return false
            }
        }
        selectedCell = null
        performTurn()
        return true
    }

    private fun cast(kingCell: ICell, destinationCell: ICell) {
        val delta = if (kingCell.column > destinationCell.column) 1 else -1
        move(kingCell, destinationCell)
        move(
            cells[destinationCell.row][destinationCell.column - delta], // Rook position
            cells[destinationCell.row][destinationCell.column + delta] // Rook destination
        )
    }

    private fun move(sourceCell: ICell, destinationCell: ICell) {
        destinationCell.piece = when (sourceCell.piece) {
            is CastingParticipant -> {
                (sourceCell.piece as CastingParticipant).wasMoved = true
                sourceCell.piece
            }

            is Pawn -> if (destinationCell.row == if (sourceCell.piece!!.color == PieceColor.WHITE) 7 else 0) {
                Queen(sourceCell.piece!!.color, theme.resources.queen)
            } else {
                sourceCell.piece
            }

            else -> sourceCell.piece
        }
        sourceCell.piece = null
    }

    private fun forEach(lambda: (value: ICell) -> Unit) = cells.flatten().forEach(lambda)

    fun simulateState(source: ICell, cell: ICell): BoardState {
//        if (selectedCell == null) return BoardState.DEFAULT
        val fakeBoard = FakeBoard(this, game.turn)
        fakeBoard.cells[source.row][source.column].piece = null
        fakeBoard.cells[cell.row][cell.column].piece = source.piece
        fakeBoard.performTurn()
        return fakeBoard.state
    }

    companion object {
        fun getDefaultPieceFor(r: Int, c: Int, theme: Theme): Piece? {
            val color = if (r > 4) PieceColor.BLACK else PieceColor.WHITE
            return when (r) {
                0, 7 -> when (c) {
                    0, 7 -> Rook(color, theme.resources.rook)
                    1, 6 -> Knight(color, theme.resources.knight)
                    2, 5 -> Bishop(color, theme.resources.bishop)
                    3 -> Queen(color, theme.resources.queen)
                    4 -> King(color, theme.resources.king)
                    else -> null
                }

                1, 6 -> Pawn(color, theme.resources.pawn, r)
                else -> null
            }
        }
    }
}