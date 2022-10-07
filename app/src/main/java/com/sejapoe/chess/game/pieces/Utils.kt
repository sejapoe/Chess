package com.sejapoe.chess.game.pieces

import android.graphics.Color

data class MovementDescription(
    val row: Int,
    val column: Int,
    val attribute: MovementAttribute = MovementAttribute.MOVE
)

enum class MovementAttribute {
    MOVE, ATTACK, CAST
}

enum class PieceColor(private val color: Int) {
    BLACK(Color.BLACK), WHITE(Color.WHITE);

    fun toInt() = color
}