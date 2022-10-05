package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import kotlin.math.abs

class Bishop(private val _color: PieceColor) : Piece {
    override fun getColor(): PieceColor = _color

    override fun canMoveTo(r1: Int, c1: Int, r2: Int, c2: Int): Boolean = abs(r2 - r1) == abs(c2 - c1)

    override fun getImageResource() = R.drawable.bishop
}