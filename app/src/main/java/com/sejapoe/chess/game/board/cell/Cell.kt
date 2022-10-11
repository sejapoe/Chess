package com.sejapoe.chess.game.board.cell

import android.widget.ImageView
import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.piece.Piece

class Cell(private val imageView: ImageView, override val board: Board, textId: String) : ICell {
    val img
        get() = imageView
    override val column = textId[0] - 'a'
    override val row = textId[1] - '1'
    private val color = if ((row + column) % 2 == 0) board.theme.colors.blackCell else board.theme.colors.whiteCell
    override var state: CellState = CellState.NONE
        set(value) {
            img.setBackgroundColor(
                when (value) {
                    CellState.NONE -> color.mainColor
                    CellState.MOVE, CellState.STAY, CellState.CAST, CellState.ATTACK -> color.selectionColor
                }
            )
            field = value
        }

    override fun resetPossibleTurns() {
        possibleTurns.forEach {
            it.replaceAll { CellState.NONE }
        }
    }

    override var possibleTurns: MutableList<MutableList<CellState>> =
        MutableList(8) { MutableList(8) { CellState.NONE } }
    override var piece: Piece? = null
        set(value) {
            when (value) {
                null -> img.setImageResource(R.drawable.cell)
                else -> {
                    img.setImageResource(value.imageResource)
                    img.setColorFilter(value.color.toInt())
                }
            }
            field = value
        }

    init {
        img.setBackgroundColor(color.mainColor)
    }

    override fun updatePossibleTurns() {
        for (i in 0..7) {
            for (j in 0..7) {
                possibleTurns[i][j] = CellState.NONE
            }
        }
        if (piece != null) {
            piece?.updatePossibleTurns(row, column, board)
            possibleTurns[row][column] = CellState.STAY
        }
    }

    fun selectPossibleTurns() {
        for (i in 0..7) {
            for (j in 0..7) {
                board.cells[i][j].state = possibleTurns[i][j]
            }
        }
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