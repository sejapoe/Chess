package com.sejapoe.chess.game.board

import android.app.Activity
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.piece.*
import com.sejapoe.chess.game.piece.core.PieceColor
import com.sejapoe.chess.game.piece.core.PieceMovement
import com.sejapoe.chess.game.theme.Theme

open class DisplayBoard(activity: Activity, val theme: Theme) : IBoard {
    override var state = BoardState.DEFAULT

    // Initialize cells, assign for each cell it's imageView
    override val cells: MutableList<MutableList<ICell>> = MutableList(8) {
        MutableList(8) { jt ->
            val textId = "${'a' + jt}${it + 1}"
            val id = activity.resources.getIdentifier(textId, "id", activity.packageName)
            Cell(activity.findViewById(id), this, textId)
        }
    }

    override val history: MutableList<PieceMovement> = mutableListOf()

    open fun resetSetup() {
        cells.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                cell.piece = getDefaultPieceFor(i, j, theme)
            }
        }
    }

    companion object {
        fun getDefaultPieceFor(r: Int, c: Int, theme: Theme): Piece? {
            val color = if (r > 4) PieceColor.BLACK else PieceColor.WHITE
            return when (r) {
                0, 7 -> when (c) {
                    0, 7 -> Rook(color, theme.resources.rook)
                    1, 6 -> Knight(color, theme.resources.knight)
                    2, 5 -> Bishop(color, theme.resources.bishop)
                    3 -> Queen(color, theme.resources.queen)
                    4 -> King(color, theme.resources.king)
                    else -> null
                }

                1, 6 -> Pawn(color, theme.resources.pawn, r)
                else -> null
            }
        }
    }
}