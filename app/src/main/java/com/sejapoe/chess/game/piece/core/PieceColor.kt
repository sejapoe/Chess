package com.sejapoe.chess.game.piece.core

import com.sejapoe.chess.game.theme.ThemeColors

enum class PieceColor() {
    BLACK() {
        override fun toInt(colors: ThemeColors) = colors.blackPiece
    },
    WHITE() {
        override fun toInt(colors: ThemeColors): Int = colors.whitePiece
    };

    abstract fun toInt(colors: ThemeColors): Int
    operator fun not(): PieceColor =
        if (this == BLACK) WHITE else BLACK
}