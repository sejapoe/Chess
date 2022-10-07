package com.sejapoe.chess.game.board

import android.widget.ImageView
import com.sejapoe.chess.R
import com.sejapoe.chess.game.pieces.Piece

enum class CellColor(val mainColor: Int, val selectionColor: Int) {
    BLACK(0xFFD18B47.toInt(), 0xFF855425.toInt()),
    WHITE(0xFFF1A963.toInt(), 0xFFA87D53.toInt());
}

class Cell(private val imageView: ImageView, textId: String) {
    val img
        get() = imageView

    val column = textId[0] - 'a'
    val row = textId[1] - '1'
    private val color = if ((row + column) % 2 == 0) CellColor.BLACK else CellColor.WHITE
    var isSelected = false
        set(value) {
            img.setBackgroundColor(if (value) color.selectionColor else color.mainColor)
            field = value
        }
    var piece: Piece? = null
        set(value) {
            when (value) {
                null -> img.setImageResource(R.drawable.cell)
                else -> {
                    img.setImageResource(value.getImageResource())
                    img.setColorFilter(value.getColor().toInt())
                }
            }
            field = value
        }

    init {
        img.setBackgroundColor(color.mainColor)
    }

    fun toggleSelection() {
        isSelected = !isSelected
    }

    fun selectAvailablePositions(board: Board) {
        val candidates = this.piece!!.getCandidateCells(this.row, this.column, board)
        candidates.forEach {
            val cell = board.cells[it.row][it.column]
            if (canMoveTo(cell)) {
                cell.isSelected = isSelected
                board.movementCandidates += it
            }
        }
    }

    private fun canMoveTo(other: Cell): Boolean {
        // TODO: Check king
        // TODO: Add attack for pawn. Check target is not ally
        return piece != null && (other.piece == null || other.piece!!.getColor() != piece!!.getColor())
    }

    fun setOnClickListener(listener: OnClickListener) {
        imageView.setOnClickListener {
            listener.onClick(this)
        }
    }

    fun interface OnClickListener {
        fun onClick(c: Cell)
    }
}