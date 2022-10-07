package com.sejapoe.chess.game.piece

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.piece.core.CastingParticipant
import com.sejapoe.chess.game.piece.core.PieceColor

class Rook(private val _color: PieceColor) : FarReachingPiece, CastingParticipant {
    override var wasMoved = false
    override fun getColor(): PieceColor = _color

    override fun selectAvailableCells(r: Int, c: Int, board: Board) {
        val allowedDirections: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (i in -1..1 step 2) {
            allowedDirections += i to 0
            allowedDirections += 0 to i
        }
        super.selectAvailableCells(r, c, board, allowedDirections)
    }

    override fun getImageResource() = R.drawable.rook
}