package com.sejapoe.chess.game

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.TextView
import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.BoardState
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.piece.core.PieceColor
import com.sejapoe.chess.game.theme.Theme
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class Game(private val activity: Activity, theme: Theme) : IGame {
    val board = Board(activity, theme, this)
    private val turnText: TextView = activity.findViewById(R.id.turnText)
    override var turn = PieceColor.WHITE
        @SuppressLint("SetTextI18n")
        set(value) {
            turnText.text = "${value.name} ${board.state.name}"
            field = value
        }

    // Add interaction logic
    init {
        for (cell in board.cells.flatten()) {
            (cell as Cell).setOnClickListener(::activateCell)
        }
    }

    private fun activateCell(it: Cell) {
        if (!board.tryMoveSelectedTo(it) && it.piece != null) {
            if (it.piece!!.color == turn) board.selectedCell = it
        }
        if (board.state == BoardState.CHECKMATE) {
            activity.finish()
        }
    }

    fun restart() {
        turn = PieceColor.WHITE
        board.resetSetup()
    }
}