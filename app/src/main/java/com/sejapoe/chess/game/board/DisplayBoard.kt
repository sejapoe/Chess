package com.sejapoe.chess.game.board

import android.app.Activity
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.cell.ICell
import com.sejapoe.chess.game.board.turn.Turn
import com.sejapoe.chess.game.piece.*
import com.sejapoe.chess.game.piece.core.PieceColor
import com.sejapoe.chess.game.theme.Theme

open class DisplayBoard(activity: Activity, val theme: Theme, isReversed: Boolean = false) : IBoard {
    override var state = BoardState.DEFAULT

    // Initialize cells, assign for each cell it's imageView
    final override val cells: MutableList<MutableList<ICell>>

    override val history: MutableList<Turn> = mutableListOf()

    init {
        val tableLayout = activity.findViewById<TableLayout>(R.id.boardHolder)
        cells = MutableList(8) {
            val rowId = if (isReversed) (7 - it) + 100 else it + 100
            val row = tableLayout.findViewById<TableRow>(rowId)
            MutableList(8) { jt ->
                val id = if (isReversed) (7 - jt) + 10 else jt + 10
                Cell(row.findViewById(id), this, "${'a' + jt}${it + 1}")
            }
        }
    }

    open fun resetSetup() {
        fillCells(DisplayBoard::getDefaultPieceFor)
    }

    fun fillCells(function: (Int, Int, Theme) -> Piece?) {
        cells.forEachIndexed { i, row ->
            row.forEachIndexed { j, cell ->
                cell.piece = function(i, j, theme)
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

        fun generateBoard(activity: Activity) {
            val tableLayout = activity.findViewById<TableLayout>(R.id.boardHolder)

            val rowParams = TableLayout.LayoutParams(tableLayout.width, tableLayout.height / 8)

            val cellParams =
                TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)

            for (i in 8 downTo 1) {
                val row = TableRow(activity)
                row.id = i + 99
                for (j in 'a'..'h') {
                    val cell = ImageView(activity)
                    cell.id = 10 + (j - 'a')
                    cell.setImageResource(R.drawable.cell)
                    row.addView(cell, cellParams)
                }
                tableLayout.addView(row, rowParams)
            }
        }
    }
}