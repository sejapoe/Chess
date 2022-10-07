package com.sejapoe.chess.game.board.cell

import android.widget.ImageView
import com.sejapoe.chess.R
import com.sejapoe.chess.game.piece.Piece

class Cell(private val imageView: ImageView, textId: String) {
    val img
        get() = imageView

    val column = textId[0] - 'a'
    val row = textId[1] - '1'
    private val color = if ((row + column) % 2 == 0) CellColor.BLACK else CellColor.WHITE
    var state: CellState = CellState.NONE
        set(value) {
            img.setBackgroundColor(
                when (value) {
                    CellState.NONE -> color.mainColor
                    CellState.MOVE, CellState.STAY, CellState.CAST, CellState.ATTACK -> color.selectionColor
                }
            )
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

    fun setOnClickListener(listener: OnClickListener) {
        imageView.setOnClickListener {
            listener.onClick(this)
        }
    }

    fun interface OnClickListener {
        fun onClick(c: Cell)
    }
}