package com.sejapoe.chess.game.piece.core

import com.sejapoe.chess.game.piece.Piece

data class PieceMovement(
    val piece: Piece,
    val columnSource: Int,
    val rowSource: Int,
    val columnDest: Int,
    val rowDest: Int,
) {
    fun toData() = PieceMovementData(columnSource, rowSource, columnDest, rowDest)
}