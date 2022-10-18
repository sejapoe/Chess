package com.sejapoe.chess.game.multiplayer

import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.Serializable

@Serializable
data class PieceMovementData(
    val columnSource: Int,
    val rowSource: Int,
    val columnDest: Int,
    val rowDest: Int,
    val performer: PieceColor
)