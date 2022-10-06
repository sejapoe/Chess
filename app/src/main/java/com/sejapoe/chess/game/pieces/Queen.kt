package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board

class Queen(private val _color: PieceColor) : FarReachingPiece {
    override fun getColor(): PieceColor = _color

    override fun getCandidateCells(r: Int, c: Int, board: Board): MutableList<Pair<Int, Int>> {
        val allowedDirections: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (i in -1..1) {
            for (j in -1..1) {
                allowedDirections += i to j
            }
        }
        return super.getCandidateCells(r, c, board, allowedDirections)
    }

    override fun getImageResource() = R.drawable.queen
}