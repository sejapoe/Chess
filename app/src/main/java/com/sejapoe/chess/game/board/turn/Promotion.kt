package com.sejapoe.chess.game.board.turn

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.multiplayer.PieceData
import kotlinx.serialization.Serializable

@Serializable
class Promotion(
    val row: Int,
    val column: Int,
    val pieceData: PieceData,
) {
    fun perform(board: Board) {
        board.cells[row][column].piece = pieceData.type(pieceData.color, board.theme)
    }
}