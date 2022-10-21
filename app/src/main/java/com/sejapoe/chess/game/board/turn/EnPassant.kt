package com.sejapoe.chess.game.board.turn

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.multiplayer.PieceMovementData
import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("en_passant")
class EnPassant(
    override val performer: PieceColor,
    private val move: PieceMovementData,
    override val number: Int,
) : Turn() {
    override fun perform(board: Board) {
        board.enPassant(
            board.cells[move.rowSource][move.columnSource],
            board.cells[move.rowDest][move.columnDest]
        )
    }
}