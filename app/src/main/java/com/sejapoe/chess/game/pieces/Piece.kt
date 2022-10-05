package com.sejapoe.chess.game.pieces

import android.graphics.Color

enum class PieceColor(private val color: Int) {
    BLACK(Color.BLACK), WHITE(Color.WHITE);

    fun toInt() = color
}

sealed interface Piece {
    fun getColor() : PieceColor
    fun canMoveTo(r1: Int, c1: Int, r2: Int, c2: Int): Boolean
    fun getImageResource(): Int
}