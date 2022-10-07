package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import com.sejapoe.chess.game.CellState
import com.sejapoe.chess.game.PieceColor
import com.sejapoe.chess.game.board.Board

class Pawn(private val _color: PieceColor, private val startRow: Int) : Piece {
    override fun getColor(): PieceColor = _color
    override fun selectAvailableCells(r: Int, c: Int, board: Board) {
        val mul = when (_color) {
            PieceColor.BLACK -> -1
            PieceColor.WHITE -> 1
        }
        if (r + mul !in 0..7) return
        board.cells[r + mul][c].setCellState(CellState.MOVE)
        if (r == startRow) board.cells[r + mul * 2][c].setCellState(CellState.MOVE)
    }

    override fun getImageResource() = R.drawable.pawn
}