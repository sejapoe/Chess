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
        val cellState1 = when {
            piece == null -> if (cellState == CellState.ATTACK) CellState.EN_PASSANT else cellState
            piece === this@Piece -> CellState.STAY
            piece!!.color == this@Piece.color -> CellState.NONE
            else -> CellState.ATTACK
        }
        board.cells[source.row][source.column].possibleTurns[row][column] =
            (if (board is Board && (board as Board).simulateState(
                    source,
                    this,
                    cellState1
                ) != BoardState.DEFAULT
            ) CellState.NONE else cellState1)
    }
}