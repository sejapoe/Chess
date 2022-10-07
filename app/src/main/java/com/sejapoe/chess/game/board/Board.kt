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
                cell.piece = defaultSetup[i][j]
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