package com.sejapoe.chess.game.piece

import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.BoardState
import com.sejapoe.chess.game.board.IBoard
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.piece.core.PieceColor

sealed interface Piece {
    val color: PieceColor
    val imageResource: Int
    fun updatePossibleTurns(r: Int, c: Int, board: IBoard)

    fun ICell.performCellState(source: ICell, cellState: CellState) {
        if (piece is King && cellState == CellState.ATTACK) {
            board.cells[source.row][source.column].possibleTurns[row][column] = CellState.ATTACK
            return
        }
        if (board.state == BoardState.CHECK_BLACK || board.state == BoardState.CHECK_WHITE) {
            if ((board as Board).simulateState(source, this) != BoardState.DEFAULT) {
                board.cells[source.row][source.column].possibleTurns[row][column] = CellState.NONE
                return
            }
        }
        board.cells[source.row][source.column].possibleTurns[row][column] = when {
            piece == null -> cellState
            piece === this@Piece -> CellState.STAY
            piece!!.color == this@Piece.color -> CellState.NONE
            else -> CellState.ATTACK
        }
    }
}