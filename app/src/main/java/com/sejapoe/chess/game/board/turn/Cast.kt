package com.sejapoe.chess.game.board.turn

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.multiplayer.PieceMovementData
import com.sejapoe.chess.game.piece.core.PieceColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("cast")
class Cast(
    override val performer: PieceColor,
    private val kingMove: PieceMovementData,
    private val rookMove: PieceMovementData,
    override val number: Int,
) : Turn() {
    override fun perform(board: Board) {
        super.perform(board)
        board.doMove(
            board.cells[kingMove.rowSource][kingMove.columnSource],
            board.cells[kingMove.rowDest][kingMove.columnDest]
        )
        board.doMove(
            board.cells[rookMove.rowSource][rookMove.columnSource],
            board.cells[rookMove.rowSource][rookMove.columnDest]
        )
    }
}