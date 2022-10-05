package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R

class King(private val _color : PieceColor) : Piece {
    override fun getColor() = _color

    override fun getMovementRules() {
        TODO("Not implemented")
    }

    override fun getImageResource() = R.drawable.king
}