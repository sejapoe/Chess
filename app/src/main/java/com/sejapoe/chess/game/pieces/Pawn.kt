package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board

class Pawn(private val _color: PieceColor, private val startRow: Int) : Piece {
    override fun getColor(): PieceColor = _color
    override fun getCandidateCells(r: Int, c: Int, board: Board): MutableList<MovementDescription> {
        val mul = when (_color) {
            PieceColor.BLACK -> -1
            PieceColor.WHITE -> 1
        }
        if (r + mul !in 0..7) return mutableListOf()
        val list = mutableListOf(MovementDescription(r + mul, c))
        if (r == startRow) list += MovementDescription(r + mul * 2, c)
        return list
    }

    override fun getImageResource() = R.drawable.pawn
}