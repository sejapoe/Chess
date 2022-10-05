package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R

class Rook(private val _color: PieceColor) : Piece {
    override fun getColor(): PieceColor = _color

    override fun canMoveTo(r1: Int, c1: Int, r2: Int, c2: Int): Boolean = r1 == r2 || c1 == c2

    override fun getImageResource() = R.drawable.rook
}