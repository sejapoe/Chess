package com.sejapoe.chess.game.board

import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.piece.King
import com.sejapoe.chess.game.piece.core.PieceMovement

interface IBoard {
    var state: BoardState
    val cells: MutableList<MutableList<ICell>>
    val history: MutableList<PieceMovement>

    fun checkForCheck() {
        if (cells.flatten().any {
                for (i in 0..7) {
                    for (j in 0..7) {
                        if (it.possibleTurns[i][j] == CellState.ATTACK && cells[i][j].piece is King && cells[i][j].piece?.color != it.piece?.color) {
                            return@any true
                        }
                    }
                }
                return@any false
            }) this.state = BoardState.CHECK
    }
}