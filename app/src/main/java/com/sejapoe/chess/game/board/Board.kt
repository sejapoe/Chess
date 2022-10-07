package com.sejapoe.chess.game.board

import android.app.Activity
import com.sejapoe.chess.game.pieces.*

class Board(activity: Activity) {
    // Initialize cells, assign for each cell it's imageView
    val cells: MutableList<MutableList<Cell>> = MutableList(8) {
        MutableList(8) { jt ->
            val textId = "${'a' + jt}${it + 1}"
            val id = activity.resources.getIdentifier(textId, "id", activity.packageName)
            Cell(activity.findViewById(id), textId)
        }
    }
    private var selectedCell: Cell? = null
        set(value) {
            resetSelection()
            if (value != null) {
                value.toggleSelection()
                value.selectAvailablePositions(this)
            }
            field = value
        }

    // Add interaction logic
    init {
        for (cell in cells.flatten()) {
            cell.setOnClickListener {
                if (!tryMoveSelectedTo(it) && it.piece != null) {
                    selectedCell = it
                }
            }
        }
    }

    // Set all cells to initial state
    fun resetSetup() {
        resetSelection()
        cells.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                cell.piece = defaultSetup[i][j]
            }
        }
    }

    private fun tryMoveSelectedTo(cell: Cell): Boolean {
        if (selectedCell == null) return false
        val fixedSelectedCell = selectedCell!!
        if (cell.piece != null && cell.piece!!.getColor() == fixedSelectedCell.piece!!.getColor()) return false // todo: attack
        if (cell.isSelected) {
            if (fixedSelectedCell.piece is Pawn &&
                cell.row == if (fixedSelectedCell.piece!!.getColor() == PieceColor.WHITE) 7 else 0
            ) {
                cell.piece = Queen(fixedSelectedCell.piece!!.getColor()) // todo: select0
            } else {
                cell.piece = fixedSelectedCell.piece
            }
            fixedSelectedCell.piece = null
        }
        selectedCell = null
        return true
    }

    private fun resetSelection() {
        forEach {
            it.isSelected = false
        }
    }

    fun forEach(foo: (value: Cell) -> Unit) {
        for (r in 0..7) {
            for (c in 0..7) {
                foo(cells[r][c])
            }
        }
    }

    companion object {
        // initial state of chessboard
        val defaultSetup = mutableListOf(
            mutableListOf(
                Rook(PieceColor.BLACK),
                Knight(PieceColor.BLACK),
                Bishop(PieceColor.BLACK),
                Queen(PieceColor.BLACK),
                King(PieceColor.BLACK),
                Bishop(PieceColor.BLACK),
                Knight(PieceColor.BLACK),
                Rook(PieceColor.BLACK)
            ),
            MutableList(8) { Pawn(PieceColor.BLACK, 6) },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { Pawn(PieceColor.WHITE, 1) },
            mutableListOf(
                Rook(PieceColor.WHITE),
                Knight(PieceColor.WHITE),
                Bishop(PieceColor.WHITE),
                Queen(PieceColor.WHITE),
                King(PieceColor.WHITE),
                Bishop(PieceColor.WHITE),
                Knight(PieceColor.WHITE),
                Rook(PieceColor.WHITE)
            ),
        ).reversed()
    }
}