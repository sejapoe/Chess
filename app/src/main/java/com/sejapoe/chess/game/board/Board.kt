package com.sejapoe.chess.game.board

import com.sejapoe.chess.game.pieces.*

class Board(private val _cells: MutableList<MutableList<Cell>>) {
    val cells
        get() = _cells

    init {

    }

    fun resetSetup() {
        _cells.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                cell.piece = defaultSetup[j][i]
            }
        }
    }

    companion object {
        val defaultSetup = mutableListOf(
            mutableListOf(Rook(PieceColor.BLACK), Knight(PieceColor.BLACK), Bishop(PieceColor.BLACK), Queen(PieceColor.BLACK), King(PieceColor.BLACK), Bishop(PieceColor.BLACK), Knight(PieceColor.BLACK), Rook(PieceColor.BLACK)),
            MutableList(8) { Pawn(PieceColor.BLACK) },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { Pawn(PieceColor.WHITE) },
            mutableListOf(Rook(PieceColor.WHITE), Knight(PieceColor.WHITE), Bishop(PieceColor.WHITE), Queen(PieceColor.WHITE), King(PieceColor.WHITE), Bishop(PieceColor.WHITE), Knight(PieceColor.WHITE), Rook(PieceColor.WHITE)),
            ).reversed()
    }
}