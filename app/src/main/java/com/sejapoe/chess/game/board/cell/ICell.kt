package com.sejapoe.chess.game.board.cell

import com.sejapoe.chess.game.board.IBoard
import com.sejapoe.chess.game.piece.Piece

interface ICell {
    fun updatePossibleTurns()
    fun resetPossibleTurns()

    val board: IBoard
    var state: CellState
    val column: Int
    val row: Int
    val possibleTurns: MutableList<MutableList<CellState>>
    var piece: Piece?
}