package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.piece.core.PieceColor

class Bishop(override val color: PieceColor, override val imageResource: Int) : FarReachingPiece {
    override fun updatePossibleTurns(r: Int, c: Int, board: Board) {
        val allowedDirections: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (i in -1..1 step 2) {
            for (j in -1..1 step 2) {
                allowedDirections += i to j
            }
        }
        super.selectAvailableCells(r, c, board, allowedDirections)
    }
}