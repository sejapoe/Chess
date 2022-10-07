package com.sejapoe.chess.game.piece

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.piece.core.PieceColor

class Queen(private val _color: PieceColor) : FarReachingPiece {
    override fun getColor(): PieceColor = _color

    override fun selectAvailableCells(r: Int, c: Int, board: Board) {
        val allowedDirections: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (i in -1..1) {
            for (j in -1..1) {
                allowedDirections += i to j
            }
        }
        super.selectAvailableCells(r, c, board, allowedDirections)
    }

    override fun getImageResource() = R.drawable.queen
}