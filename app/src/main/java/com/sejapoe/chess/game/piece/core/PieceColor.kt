package com.sejapoe.chess.game.piece.core

import android.graphics.Color

enum class PieceColor(private val color: Int) {
    BLACK(Color.BLACK), WHITE(Color.WHITE);

    fun toInt() = color
}