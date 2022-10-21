package com.sejapoe.chess.game.board.turn

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.multiplayer.PieceMovementData
import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("move")
class Move(
    override val performer: PieceColor,
    val move: PieceMovementData,
    override val number: Int,
) : Turn() {
    override fun perform(board: Board) {
        board.move(
            board.cells[move.rowSource][move.columnSource],
            board.cells[move.rowDest][move.columnDest]
        )
    }

    override fun toString(): String {
        return "Move(performer=$performer, move=$move, number=$number)\n"
    }
}