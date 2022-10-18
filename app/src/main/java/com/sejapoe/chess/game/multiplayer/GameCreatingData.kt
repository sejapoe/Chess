package com.sejapoe.chess.game.multiplayer

import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.Serializable

@Serializable
data class GameCreatingData(
    val id: Long,
    val yourColor: PieceColor
)