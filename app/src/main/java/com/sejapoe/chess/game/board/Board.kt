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
    val movementCandidates: MutableList<MovementDescription> = mutableListOf()

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
                cell.piece = getDefaultPieceFor(i, j)
            }
        }
    }

    private fun tryMoveSelectedTo(destinationCell: Cell): Boolean {
        if (selectedCell == null) return false
        val fixedSelectedCell = selectedCell!!

        if (destinationCell.piece != null && destinationCell.piece!!.getColor() == fixedSelectedCell.piece!!.getColor()) return false // todo: attack

        val movementDescription =
            movementCandidates.find { it.row == destinationCell.row && it.column == destinationCell.column }
        if (movementDescription != null) {
            when (movementDescription.attribute) {
                MovementAttribute.MOVE -> move(fixedSelectedCell, destinationCell)
                MovementAttribute.ATTACK -> TODO()
                MovementAttribute.CAST -> cast(fixedSelectedCell, destinationCell)
            }
        }
        selectedCell = null
        return true
    }

    private fun cast(kingCell: Cell, destinationCell: Cell) {
        val delta = if (kingCell.column > destinationCell.column) 1 else -1
        move(kingCell, destinationCell)
        move(
            cells[destinationCell.row][destinationCell.column - delta],
            cells[destinationCell.row][destinationCell.column + delta]
        )
    }

    private fun move(sourceCell: Cell, destinationCell: Cell) {
        when (sourceCell.piece) {
            is CastingParticipant -> {
                (sourceCell.piece as CastingParticipant).wasMoved = true
                destinationCell.piece = sourceCell.piece
            }

            is Pawn -> if (destinationCell.row == if (sourceCell.piece!!.getColor() == PieceColor.WHITE) 7 else 0) {
                destinationCell.piece = Queen(sourceCell.piece!!.getColor())
            } else {
                destinationCell.piece = sourceCell.piece
            }

            else -> destinationCell.piece = sourceCell.piece
        }
        sourceCell.piece = null
    }

    private fun resetSelection() {
        movementCandidates.clear()
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
        fun getDefaultPieceFor(r: Int, c: Int): Piece? {
            val color = if (r > 4) PieceColor.BLACK else PieceColor.WHITE
            return when (r) {
                0, 7 -> when (c) {
                    0, 7 -> Rook(color)
                    1, 6 -> Knight(color)
                    2, 5 -> Bishop(color)
                    3 -> Queen(color)
                    4 -> King(color)
                    else -> null
                }

                1, 6 -> Pawn(color, c)
                else -> null
            }
        }
    }
}