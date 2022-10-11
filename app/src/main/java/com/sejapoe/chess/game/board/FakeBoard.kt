package com.sejapoe.chess.game.board

import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.FakeCell
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.piece.core.PieceColor

class FakeBoard(board: Board, private val turn: PieceColor) : IBoard {
    override val cells: MutableList<MutableList<ICell>> = MutableList(8) { i ->
        MutableList(8) { j ->
            FakeCell(board.cells[i][j] as Cell, this)
        }
    }
    override var state: BoardState = BoardState.DEFAULT

    fun performTurn() {
        this.state = BoardState.DEFAULT
        cells.flatten().forEach { it.resetPossibleTurns() }
        cells.flatten().filter { it.piece?.color == turn }.forEach {
            it.updatePossibleTurns()
        }
        this.checkForCheck()
    }
}
