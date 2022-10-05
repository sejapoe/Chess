package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R

class Pawn(private val _color: PieceColor, private val startRow: Int) : Piece {
    override fun getColor(): PieceColor = _color

    override fun canMoveTo(r1: Int, c1: Int, r2: Int, c2: Int): Boolean {
        val mul = if (getColor() == PieceColor.BLACK) -1 else 1
        return (c1 == c2) && (mul * (r2 - r1) > 0) && (mul * (r2 - r1) <= (if (startRow == r1) 2 else 1))
    }

    override fun getImageResource() = R.drawable.pawn
}