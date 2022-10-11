package com.sejapoe.chess.game.board.cell

import com.sejapoe.chess.game.board.IBoard
import com.sejapoe.chess.game.piece.Piece

interface ICell {
    fun updatePossibleTurns() {
        for (i in 0..7) {
            for (j in 0..7) {
                possibleTurns[i][j] = CellState.NONE
            }
        }
        if (piece != null) {
            piece?.updatePossibleTurns(row, column, board)
            possibleTurns[row][column] = CellState.STAY
        }
    }

    fun resetPossibleTurns() {
        possibleTurns.forEach {
            it.replaceAll { CellState.NONE }
        }
    }

    val board: IBoard
    var state: CellState
    val column: Int
    val row: Int
    val possibleTurns: MutableList<MutableList<CellState>>
    var piece: Piece?
}