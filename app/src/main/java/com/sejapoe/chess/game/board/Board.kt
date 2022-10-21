package com.sejapoe.chess.game.board

import android.app.Activity
import com.sejapoe.chess.game.IGame
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.CellState
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.board.turn.*
import com.sejapoe.chess.game.multiplayer.PieceMovementData
import com.sejapoe.chess.game.piece.King
import com.sejapoe.chess.game.piece.Pawn
import com.sejapoe.chess.game.piece.Queen
import com.sejapoe.chess.game.piece.core.CastingParticipant
import com.sejapoe.chess.game.piece.core.PieceColor
import com.sejapoe.chess.game.theme.Theme

class Board(activity: Activity, theme: Theme, val game: IGame, isReversed: Boolean = false) :
    DisplayBoard(activity, theme, isReversed) {
    override val history: MutableList<Turn> = mutableListOf()

    override var state: BoardState = BoardState.DEFAULT

    var selectedCell: Cell? = null
        set(value) {
            forEach {
                it.state = CellState.NONE
            }
            if (value != null) {
                value.state = CellState.STAY
                value.selectPossibleTurns()
            }
            field = value
        }


    // Set all cells to initial state
    override fun resetSetup() {
        selectedCell = null // Reset selection
        super.resetSetup()
        cells.flatten().forEach { it.updatePossibleTurns() }
        this.state = BoardState.DEFAULT
        game.turn = PieceColor.WHITE
    }

    fun performTurn() {
        this.state = BoardState.DEFAULT
        cells.flatten().forEach { it.resetPossibleTurns() }
        cells.flatten().filter { it.piece?.color == game.turn }.forEach {
            it.updatePossibleTurns()
        }
        this.checkForCheck()
        var flag = true
        cells.flatten().forEach {
            it.updatePossibleTurns()
            if (flag && it.piece?.color == !game.turn && !it.possibleTurns.flatten()
                    .all { jt -> jt == CellState.NONE || jt == CellState.STAY }
            ) {
                flag = false
            }
        }
        if (flag) {
            this.state = if (this.state == BoardState.CHECK) BoardState.CHECKMATE else BoardState.DRAW
        }
        cells.flatten().filter { it.piece is King }.forEach {
            it.updatePossibleTurns()
        }
        game.turn = !game.turn
    }

    fun tryMoveSelectedTo(destinationCell: Cell): Boolean {
        val fixedSelectedCell = selectedCell ?: return false

        when (destinationCell.state) {
            CellState.MOVE -> move(fixedSelectedCell, destinationCell)
            CellState.ATTACK -> {
                if (destinationCell.piece == null) enPassant(fixedSelectedCell, destinationCell)
                else attack(fixedSelectedCell, destinationCell)
            }

            CellState.CAST -> cast(fixedSelectedCell, destinationCell)
            else -> {
                selectedCell = null
                return false
            }
        }
        selectedCell = null
        performTurn()
        return true
    }

    fun move(sourceCell: ICell, destinationCell: ICell) {
        doMove(sourceCell, destinationCell)
        history.add(Move(game.turn, PieceMovementData(sourceCell, destinationCell), history.size))
    }

    fun attack(sourceCell: ICell, destinationCell: ICell) { // TODO: ??
        doMove(sourceCell, destinationCell)
        history.add(Attack(game.turn, PieceMovementData(sourceCell, destinationCell), history.size))
    }

    private fun cast(kingCell: ICell, destinationCell: ICell) {
        val delta = if (kingCell.column > destinationCell.column) 1 else -1
        doMove(kingCell, destinationCell)

        val rookSource = cells[destinationCell.row][destinationCell.column - delta]
        val rookDestination = cells[destinationCell.row][destinationCell.column + delta]
        doMove(
            rookSource,
            rookDestination
        )

        history.add(
            Cast(
                game.turn,
                PieceMovementData(kingCell, destinationCell),
                PieceMovementData(rookSource, rookDestination),
                history.size
            )
        )
    }

    fun enPassant(
        sourceCell: ICell,
        destinationCell: ICell,
    ) {
        val cell = cells[sourceCell.row][destinationCell.column]
        // TODO: log killed pawn
        doMove(cell, null)
        doMove(sourceCell, destinationCell)
        history.add(EnPassant(game.turn, PieceMovementData(sourceCell, destinationCell), history.size))
    }

    fun doMove(sourceCell: ICell, destinationCell: ICell?) {
        destinationCell?.piece = when (sourceCell.piece) {
            is CastingParticipant -> {
                (sourceCell.piece as CastingParticipant).wasMoved = true
                sourceCell.piece
            }

            is Pawn -> if (destinationCell?.row == if (sourceCell.piece!!.color == PieceColor.WHITE) 7 else 0) {
                Queen(sourceCell.piece!!.color, theme.resources.queen)
            } else {
                sourceCell.piece
            }

            else -> sourceCell.piece
        }
        sourceCell.piece = null
    }

    private fun forEach(lambda: (value: ICell) -> Unit) = cells.flatten().forEach(lambda)

    fun simulateState(source: ICell, cell: ICell, cellState: CellState): BoardState {
        if (source.piece == null) return this.state
        if (source.piece?.color == game.turn) return BoardState.DEFAULT
        if (cellState == CellState.CAST) {
            if (this.state == BoardState.CHECK) return BoardState.CHECK
            return if (cells.flatten().filter { it.piece?.color != source.piece?.color }.none {
                    (if (cell.column < source.column) -3..-1 else 1..2).any { i ->
                        it.possibleTurns[source.row][i + source.column] != CellState.NONE
                    }
                }) BoardState.DEFAULT else BoardState.CHECK
        }
        val fakeBoard = FakeBoard(this, game.turn)
        if (fakeBoard.cells[cell.row][cell.column].piece == null || cellState == CellState.ATTACK) {
            fakeBoard.cells[source.row][source.column].piece = null
            fakeBoard.cells[cell.row][cell.column].piece = source.piece
        }
        fakeBoard.performTurn()
        return fakeBoard.state
    }

}