package com.sejapoe.chess.game.board.turn

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.Serializable

@Serializable
sealed class Turn() {
    abstract val number: Int
    abstract val performer: PieceColor
    var promotion: Promotion? = null
    open fun perform(board: Board) {
        promotion?.perform(board)
    }
}