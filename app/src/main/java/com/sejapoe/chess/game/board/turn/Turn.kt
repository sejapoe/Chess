package com.sejapoe.chess.game.board.turn

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.Serializable

@Serializable
sealed class Turn() {
    abstract val number: Int
    abstract val performer: PieceColor
    abstract fun perform(board: Board)
}