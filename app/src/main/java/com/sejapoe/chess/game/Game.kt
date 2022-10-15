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
import kotlinx.coroutines.runBlocking
import java.util.logging.Logger

class Game(activity: Activity, theme: Theme) {
    val board = Board(activity, theme, this)
    private val turnText: TextView = activity.findViewById<TextView>(R.id.turnText)
    var turn = PieceColor.WHITE
        @SuppressLint("SetTextI18n")
        set(value) {
            turnText.text = "${value.name} ${board.state.name}"
            field = value
        }

    // Add interaction logic
    init {
        for (cell in board.cells.flatten()) {
            (cell as Cell).setOnClickListener {
                if (board.tryMoveSelectedTo(it)) {
                    runBlocking {
                        try {
                            HttpClient().request("http://192.168.0.15:8080/move").run {}
                        } catch (e: Exception) {
                            Logger.getGlobal().info("Server not found")
                        }
                    }
                } else if (it.piece != null) {
                    if (it.piece!!.color == turn) board.selectedCell = it
                }
                if (board.state == BoardState.CHECKMATE) {
                    activity.finish()
                }
            }
        }
    }

    fun restart() {
        turn = PieceColor.WHITE
        board.resetSetup()
    }
}