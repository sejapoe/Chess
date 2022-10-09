package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.piece.core.PieceColor

class Pawn(override val color: PieceColor, override val imageResource: Int, private val startRow: Int) : Piece {
    override fun selectAvailableCells(r: Int, c: Int, board: Board) {
        val mul = when (color) {
            PieceColor.BLACK -> -1
            PieceColor.WHITE -> 1
        }
        if (r + mul !in 0..7) return
        if (board.cells[r + mul][c].piece == null) board.cells[r + mul][c].setCellState(CellState.MOVE)
        if (c != 0 && board.cells[r + mul][c - 1].piece != null) board.cells[r + mul][c - 1].setCellState(CellState.ATTACK)
        if (c != 7 && board.cells[r + mul][c + 1].piece != null) board.cells[r + mul][c + 1].setCellState(CellState.ATTACK)
        if (r == startRow) board.cells[r + mul * 2][c].setCellState(CellState.MOVE)
    }
}