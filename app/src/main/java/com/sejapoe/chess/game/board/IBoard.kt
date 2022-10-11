package com.sejapoe.chess.game.board

import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.piece.King

interface IBoard {
    var state: BoardState
    val cells: MutableList<MutableList<ICell>>

    fun checkForCheck() {
        if (cells.flatten().any {
                for (i in 0..7) {
                    for (j in 0..7) {
                        if (it.possibleTurns[i][j] == CellState.ATTACK && cells[i][j].piece is King) {
                            return@any true
                        }
                    }
                }
                return@any false
            }) this.state = BoardState.CHECK
    }
}