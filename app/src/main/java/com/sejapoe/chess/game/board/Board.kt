package com.sejapoe.chess.game.board

import android.app.Activity
import com.sejapoe.chess.game.pieces.*

class Board(activity: Activity) {
    private val cells: MutableList<MutableList<Cell>> = MutableList(8) {
        MutableList(8) {jt ->
            val textId = "${'a' + jt}${it + 1}"
            val id = activity.resources.getIdentifier(textId, "id", activity.packageName)
            Cell(activity.findViewById(id), textId)
        }
    }
    private var selectedCell: Cell? = null
        set(value) {
            if (value != null) {
                value.toggleSelection()
                value.selectAvailablePositions(this)
            } else {
                resetSelection()
            }
            field = value
        }

    init {
        for (cell in cells.flatten()) {
            cell.setOnClickListener {
                if(!tryMoveSelectedTo(it) && it.piece != null) {
                    selectedCell = it
                }
            }
        }
    }

    fun resetSetup() {
        cells.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                cell.piece = defaultSetup[i][j]
            }
        }
    }

    private fun tryMoveSelectedTo(cell: Cell): Boolean {
        if(selectedCell == null) return false
        if (cell.piece != null && cell.piece!!.getColor() == selectedCell!!.piece!!.getColor()) return false // todo: attack
        if (cell.isSelected) {
            cell.piece = selectedCell!!.piece
            selectedCell!!.piece = null
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
        val defaultSetup = mutableListOf(
            mutableListOf(Rook(PieceColor.BLACK), Knight(PieceColor.BLACK), Bishop(PieceColor.BLACK), Queen(PieceColor.BLACK), King(PieceColor.BLACK), Bishop(PieceColor.BLACK), Knight(PieceColor.BLACK), Rook(PieceColor.BLACK)),
            MutableList(8) { Pawn(PieceColor.BLACK, 6) },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { null },
            MutableList(8) { Pawn(PieceColor.WHITE, 1) },
            mutableListOf(Rook(PieceColor.WHITE), Knight(PieceColor.WHITE), Bishop(PieceColor.WHITE), Queen(PieceColor.WHITE), King(PieceColor.WHITE), Bishop(PieceColor.WHITE), Knight(PieceColor.WHITE), Rook(PieceColor.WHITE)),
            ).reversed()
    }
}