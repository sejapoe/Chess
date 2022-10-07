package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.game.board.Board

sealed interface Piece {
    fun getColor(): PieceColor
    fun getCandidateCells(r: Int, c: Int, board: Board): MutableList<MovementDescription>
    fun getImageResource(): Int
}