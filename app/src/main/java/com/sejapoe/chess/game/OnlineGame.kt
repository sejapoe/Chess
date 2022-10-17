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
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class OnlineGame(
    private val activity: Activity,
    theme: Theme,
    private val id: Long,
    private val localPlayerColor: PieceColor,
) :
    IGame {
    val board = Board(activity, theme, this)
    private val turnText: TextView = activity.findViewById(R.id.turnText)
    override var turn = PieceColor.WHITE
        @SuppressLint("SetTextI18n")
        set(value) {
            turnText.text = "${value.name} ${board.state.name}"
            field = value
        }
    private var moves = 0
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    // Add interaction logic
    init {
        for (cell in board.cells.flatten()) {
            (cell as Cell).setOnClickListener(::activateCell)
        }
    }

    private fun activateCell(it: Cell) {
        if (board.selectedCell?.piece?.color != localPlayerColor && it.piece?.color != localPlayerColor) {
            if (board.selectedCell != null) board.selectedCell = null
            return
        }
        if (board.tryMoveSelectedTo(it)) {
            sendMoves()
        } else if (it.piece != null) {
            if (it.piece!!.color == turn) board.selectedCell = it
        }
        if (board.state == BoardState.CHECKMATE) {
            activity.finish()
        }
    }

    private fun sendMoves() {
        CoroutineScope(Dispatchers.Default).launch {
            while (moves < board.history.size) {
                httpClient.request {
                    method = HttpMethod.Post
                    url("http://192.168.0.15:8080/game/$id/move")
                    contentType(ContentType.Application.Json)
                    setBody(board.history[moves++].toData())
                }.run {
                    println(status)
                }
            }
        }
    }

    fun restart() {
        turn = PieceColor.WHITE
        board.resetSetup()
    }
}