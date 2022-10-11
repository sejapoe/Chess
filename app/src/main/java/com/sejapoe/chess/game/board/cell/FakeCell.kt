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
}