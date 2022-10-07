package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.game.board.Board
interface FarReachingPiece : Piece {
    fun getCandidateCells(
        r: Int,
        c: Int,
        board: Board,
        directions: Set<Pair<Int, Int>>
    ): MutableList<MovementDescription> {
        val allowedDirections = directions.toHashSet()
        val list: MutableList<MovementDescription> = mutableListOf()
        for (i in 1..7) {
            for (dir in directions) {
                if (allowedDirections.contains(dir)) {
                    val rDest = r + i * dir.first
                    val cDest = c + i * dir.second
                    if (rDest !in 0..7 || cDest !in 0..7) {
                        allowedDirections.remove(dir)
                        continue
                    }
                    if (board.cells[rDest][cDest].piece != null) {
                        allowedDirections.remove(dir)
                    }
                    list += MovementDescription(rDest, cDest)
                }
            }
        }
        return list
    }
}