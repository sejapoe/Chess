package com.sejapoe.chess.game.multiplayer

import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.Serializable

@Serializable
data class BoardData(
    val cells: MutableList<MutableList<PieceData?>>,
    val turn: PieceColor,
    val turnCount: Int
)