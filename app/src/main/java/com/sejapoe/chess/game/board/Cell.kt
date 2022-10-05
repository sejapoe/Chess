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

    private val row = textId[0] - 'a'
    private val column = textId[1] - '1'
    private val color = if ((row + column) % 2 == 0) CellColor.BLACK else CellColor.WHITE
    private var isSelected = false
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
        img.setBackgroundColor(if(isSelected) color.mainColor else color.selectionColor)
        isSelected = !isSelected
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