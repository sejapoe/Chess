package com.sejapoe.chess.game.board

import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.board.cell.FakeCell
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.piece.King
import com.sejapoe.chess.game.piece.core.PieceColor

class FakeBoard(board: Board) : IBoard {
    override val cells: MutableList<MutableList<ICell>> = MutableList(8) { i ->
        MutableList(8) { j ->
            FakeCell(board.cells[i][j] as Cell, this)
        }
    }
    override var state: BoardState = BoardState.DEFAULT

    fun performTurn() {
        this.state = BoardState.DEFAULT
        val whiteKing = cells.flatten().find { it.piece is King && it.piece?.color == PieceColor.WHITE }
        val blackKing = cells.flatten().find { it.piece is King && it.piece?.color == PieceColor.BLACK }
        cells.flatten().forEach {
            it.updatePossibleTurns()
        }
        for (it in cells.flatten()) {
            if (whiteKing != null) {
                if (it.possibleTurns[whiteKing.row][whiteKing.column] == CellState.ATTACK) {
                    this.state = BoardState.CHECK_WHITE
                    break
                }
            }
            if (blackKing != null) {
                if (it.possibleTurns[blackKing.row][blackKing.column] == CellState.ATTACK) {
                    this.state = BoardState.CHECK_BLACK
                    break
                }
            }
        }
    }
}
