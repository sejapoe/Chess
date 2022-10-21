package com.sejapoe.chess.game

import android.annotation.SuppressLint
import android.app.Activity
import android.media.MediaPlayer
import android.os.Looper
import android.widget.TextView
import com.sejapoe.chess.App
import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.BoardState
import com.sejapoe.chess.game.board.cell.Cell
import com.sejapoe.chess.game.board.turn.Turn
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnlineGame(
    private val activity: Activity,
    theme: Theme,
    private val id: Long,
    private val localPlayerColor: PieceColor,
) :
    IGame {
    val board = Board(activity, theme, this, localPlayerColor == PieceColor.BLACK)
    private val turnText: TextView = activity.findViewById(R.id.turnText)
    override var turn = PieceColor.WHITE
        @SuppressLint("SetTextI18n")
        set(value) {
            if (Looper.getMainLooper() == Looper.myLooper())
                turnText.text = "YOU = $localPlayerColor | ${value.name} ${board.state.name}"
            field = value
        }
    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }
    private val player = MediaPlayer.create(
        activity.applicationContext,
        activity.resources.getIdentifier("beep", "raw", activity.packageName)
    )

    // Add interaction logic
    init {
        for (cell in board.cells.flatten()) {
            (cell as Cell).setOnClickListener(::activateCell)
        }
        httpClient.launch {
            while (true) {
                delay(500)
                httpClient.request {
                    url("${App.HOST}/game/$id")
                }.run {
                    // TODO: 404 check
                    val data = try {
                        call.body<Turn>()
                    } catch (e: Exception) {
                        null
                    }
                    if (data != null
                        && data.number == board.history.size
                        && data.performer != localPlayerColor
                        && turn != localPlayerColor
                    ) {
                        data.perform(board)
                        board.performTurn()
                        player.start()
                    }
                }
            }
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
            httpClient.request {
                method = HttpMethod.Post
                url("${App.HOST}/game/$id/move")
                contentType(ContentType.Application.Json)
                setBody(board.history.last())
            }
        }
    }

    fun restart() {
        turn = PieceColor.WHITE
        board.resetSetup()
    }
}