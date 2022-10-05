package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import kotlin.math.abs

class Knight(private val _color: PieceColor) : Piece {
    override fun getColor(): PieceColor = _color

    override fun canMoveTo(r1: Int, c1: Int, r2: Int, c2: Int) = setOf(abs(r1 - r2), abs(c1 - c2)) == setOf(1, 2)

    override fun getImageResource() = R.drawable.knight
}