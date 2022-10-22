package com.sejapoe.chess.game.board.turn

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.multiplayer.PieceMovementData
import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("attack")
class Attack(
    override val performer: PieceColor,
    val move: PieceMovementData,
    override val number: Int,
) : Turn() {
    override fun perform(board: Board) {
        super.perform(board)
        board.attack(
            board.cells[move.rowSource][move.columnSource],
            board.cells[move.rowDest][move.columnDest]
        )
    }
}