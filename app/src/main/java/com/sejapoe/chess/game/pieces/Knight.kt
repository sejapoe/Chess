package com.sejapoe.chess.game.pieces

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board

class Knight(private val _color: PieceColor) : Piece {
    override fun getColor(): PieceColor = _color

    override fun getCandidateCells(r: Int, c: Int, board: Board): MutableList<MovementDescription> {
        val list: MutableList<MovementDescription> = mutableListOf()
        for (i in -1..1 step 2) {
            for (j in -1..1 step 2) {
                val rDest1 = r + i
                val cDest1 = c + j * 2
                if (rDest1 in 0..7 && cDest1 in 0..7) list += MovementDescription(rDest1, cDest1)

                val rDest2 = r + i * 2
                val cDest2 = c + j
                if (rDest2 in 0..7 && cDest2 in 0..7) list += MovementDescription(rDest2, cDest2)
            }
        }
        return list
    }

    override fun getImageResource() = R.drawable.knight
}