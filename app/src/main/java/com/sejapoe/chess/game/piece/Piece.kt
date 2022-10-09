package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.piece.core.PieceColor

sealed interface Piece {
    val color: PieceColor
    val imageResource: Int
    fun updatePossibleTurns(r: Int, c: Int, board: Board)

    fun Cell.performCellState(cellState: CellState): CellState = when {
        piece == null -> cellState
        piece === this@Piece -> CellState.STAY
        piece!!.color == this@Piece.color -> CellState.NONE
        else -> CellState.ATTACK
    }
}