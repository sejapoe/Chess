package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.IBoard
import com.sejapoe.chess.game.board.cell.CellState

interface FarReachingPiece : Piece {
    fun selectAvailableCells(
        r: Int,
        c: Int,
        board: IBoard,
        directions: Set<Pair<Int, Int>>
    ) {
        val allowedDirections = directions.toHashSet()
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
                    board.cells[rDest][cDest].performCellState(board.cells[r][c], CellState.MOVE)
                }
            }
        }
    }
}