package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.IBoard
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.piece.core.CastingParticipant
import com.sejapoe.chess.game.piece.core.PieceColor

class King(override val color: PieceColor, override val imageResource: Int) : Piece, CastingParticipant {
    override var wasMoved = false

    override fun updatePossibleTurns(r: Int, c: Int, board: IBoard) {
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val rDest = r + i
                val cDest = c + j
                if (rDest in 0..7 && cDest in 0..7) board.cells[rDest][cDest].performCellState(
                    board.cells[r][c],
                    CellState.MOVE
                )
            }
        }
        if (c == 4 && !wasMoved) {
            if (
                (-3..-1).all {
                    board.cells[r][it + c].piece == null
                } &&
                board.cells[r][0].piece is Rook &&
                !(board.cells[r][0].piece as Rook).wasMoved
            )
                board.cells[r][c - 3].performCellState(board.cells[r][c], CellState.CAST)
            if (
                (1..2).all {
                    board.cells[r][it + c].piece == null
                } &&
                board.cells[r][7].piece is Rook &&
                !(board.cells[r][7].piece as Rook).wasMoved
            )
                board.cells[r][c + 2].performCellState(board.cells[r][c], CellState.CAST)
        }
    }
}