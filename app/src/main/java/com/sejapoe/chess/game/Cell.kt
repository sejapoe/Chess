package com.sejapoe.chess.game

import android.widget.ImageView

enum class CellColor(val mainColor: Int, val selectionColor: Int) {
    BLACK(0xFF000000.toInt(), 0xFF7E4A00.toInt()),
    WHITE(0xFFFFFFFF.toInt(), 0xFFFF9805.toInt())
}

class Cell(private val imageView: ImageView, textId: String) {
    val img
        get() = imageView

    val row = textId[0] - 'a'
    val column = textId[1] - '1'
    val color = CellColor.values()[((row + column) % 2)]
    var isSelected = false

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