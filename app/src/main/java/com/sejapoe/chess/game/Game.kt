package com.sejapoe.chess.game

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.TextView
import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.piece.core.PieceColor
import com.sejapoe.chess.game.theme.Theme

class Game(activity: Activity, theme: Theme) {
    val board = Board(activity, theme)
    private val turnText: TextView = activity.findViewById<TextView>(R.id.turnText)
    private var turn = PieceColor.WHITE
        @SuppressLint("SetTextI18n")
        set(value) {
            turnText.text = "${value.name} ${board.state.name}"
            field = value
        }

    // Add interaction logic
    init {
        for (cell in board.cells.flatten()) {
            cell.setOnClickListener {
                if (board.tryMoveSelectedTo(it)) {
                    turn = !turn
                } else if (it.piece != null) {
                    if (it.piece!!.color == turn) board.selectedCell = it
                }
            }
        }
    }

    fun restart() {
        turn = PieceColor.WHITE
        board.resetSetup()
    }
}