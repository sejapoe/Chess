package com.sejapoe.chess.game.pieces

import android.graphics.Color

enum class PieceColor(private val color: Int) {
    BLACK(Color.BLACK), WHITE(Color.WHITE);

    fun toInt() = color
}

sealed interface Piece {
    fun getColor() : PieceColor
    fun getMovementRules()
    fun getImageResource(): Int
}