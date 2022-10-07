package com.sejapoe.chess.game.piece

import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.piece.core.PieceColor

class Knight(override val color: PieceColor) : Piece {
    override val imageResource get() = R.drawable.knight

    override fun selectAvailableCells(r: Int, c: Int, board: Board) {
        for (i in -1..1 step 2) {
            for (j in -1..1 step 2) {
                val rDest1 = r + i
                val cDest1 = c + j * 2
                if (rDest1 in 0..7 && cDest1 in 0..7) board.cells[rDest1][cDest1].setCellState(CellState.MOVE)

                val rDest2 = r + i * 2
                val cDest2 = c + j
                if (rDest2 in 0..7 && cDest2 in 0..7) board.cells[rDest2][cDest2].setCellState(CellState.MOVE)
            }
        }
    }
}