package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board

class King(private val _color: PieceColor) : Piece {
    override fun getColor() = _color

    override fun getCandidateCells(r: Int, c: Int, board: Board): MutableList<Pair<Int, Int>> {
        val list: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val rDest = r + i
                val cDest = c + j
                if (rDest in 0..7 && cDest in 0..7) list += rDest to cDest
            }
        }
        return list
    }

    override fun getImageResource() = R.drawable.king
}