package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R

class Knight(private val _color: PieceColor) : Piece {
    override fun getColor(): PieceColor = _color

    override fun getMovementRules() {
        TODO("Not yet implemented")
    }

    override fun getImageResource() = R.drawable.knight
}