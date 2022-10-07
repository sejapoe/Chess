package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import com.sejapoe.chess.game.CellState
import com.sejapoe.chess.game.PieceColor
import com.sejapoe.chess.game.board.Board

class King(private val _color: PieceColor) : Piece, CastingParticipant {
    override var wasMoved = false

    override fun getColor() = _color

    override fun selectAvailableCells(r: Int, c: Int, board: Board) {
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val rDest = r + i
                val cDest = c + j
                if (rDest in 0..7 && cDest in 0..7) board.cells[rDest][cDest].setCellState(CellState.MOVE)
            }
        }
        if (c == 4 && !wasMoved) {
            if (
                (-3..-1).all {
                    board.cells[r][it + c].piece == null
                } &&
                board.cells[r][0].piece is Rook &&
                !(board.cells[r][0].piece as Rook).wasMoved
            )
                board.cells[r][c - 3].setCellState(CellState.CAST)
            if (
                (1..2).all {
                    board.cells[r][it + c].piece == null
                } &&
                board.cells[r][7].piece is Rook &&
                !(board.cells[r][7].piece as Rook).wasMoved
            )
                board.cells[r][c + 2].setCellState(CellState.CAST)
        }
    }

    override fun getImageResource() = R.drawable.king
}