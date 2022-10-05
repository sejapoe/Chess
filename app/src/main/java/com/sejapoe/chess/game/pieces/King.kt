package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import kotlin.math.abs

class King(private val _color: PieceColor) : Piece {
    override fun getColor() = _color

    override fun canMoveTo(r1: Int, c1: Int, r2: Int, c2: Int): Boolean = abs(r1 - r2) <= 1 && abs(c1 - c2) <= 1

    override fun getImageResource() = R.drawable.king
}