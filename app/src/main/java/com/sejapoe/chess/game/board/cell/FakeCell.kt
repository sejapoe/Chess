package com.sejapoe.chess.game.board.cell

import com.sejapoe.chess.game.board.IBoard
import com.sejapoe.chess.game.piece.Piece

class FakeCell(cell: Cell, override val board: IBoard) : ICell {
    override val column: Int = cell.column
    override val row: Int = cell.row
    override var state: CellState = CellState.NONE
    override var possibleTurns: MutableList<MutableList<CellState>> =
        MutableList(8) { MutableList(8) { CellState.NONE } }
    override var piece: Piece? = cell.piece
    override fun resetPossibleTurns() {
        possibleTurns.forEach {
            it.replaceAll { CellState.NONE }
        }
    }

    override fun updatePossibleTurns() {
        for (i in 0..7) {
            for (j in 0..7) {
                possibleTurns[i][j] = CellState.NONE
            }
        }
        if (piece != null) {
            piece?.updatePossibleTurns(row, column, board)
            possibleTurns[row][column] = CellState.STAY
        }
    }
}