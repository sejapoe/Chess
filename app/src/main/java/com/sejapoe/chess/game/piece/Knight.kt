package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.IBoard
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.piece.core.PieceColor

class Knight(override val color: PieceColor, override val imageResource: Int) : Piece {
    override fun updatePossibleTurns(r: Int, c: Int, board: IBoard) {
        for (i in -1..1 step 2) {
            for (j in -1..1 step 2) {
                val rDest1 = r + i
                val cDest1 = c + j * 2
                if (rDest1 in 0..7 && cDest1 in 0..7) board.cells[rDest1][cDest1].performCellState(
                    board.cells[r][c],
                    CellState.MOVE
                )

                val rDest2 = r + i * 2
                val cDest2 = c + j
                if (rDest2 in 0..7 && cDest2 in 0..7) board.cells[rDest2][cDest2].performCellState(
                    board.cells[r][c],
                    CellState.MOVE
                )
            }
        }
    }
}