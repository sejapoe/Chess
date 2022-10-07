package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.piece.core.PieceColor

sealed interface Piece {
    fun getColor(): PieceColor
    fun selectAvailableCells(r: Int, c: Int, board: Board)
    fun getImageResource(): Int

    fun Cell.setCellState(cellState: CellState) {
        state = when {
            piece == null -> cellState
            piece === this@Piece -> CellState.STAY
            piece!!.getColor() == this@Piece.getColor() -> CellState.NONE
            else -> CellState.ATTACK
        }
    }
}