package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board

class Rook(private val _color: PieceColor) : FarReachingPiece, CastingParticipant {
    override var wasMoved = false
    override fun getColor(): PieceColor = _color

    override fun getCandidateCells(r: Int, c: Int, board: Board): MutableList<MovementDescription> {
        val allowedDirections: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (i in -1..1 step 2) {
            allowedDirections += i to 0
            allowedDirections += 0 to i
        }
        return super.getCandidateCells(r, c, board, allowedDirections)
    }

    override fun getImageResource() = R.drawable.rook
}