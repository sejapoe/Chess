package com.sejapoe.chess

import android.os.Bundle
import com.sejapoe.chess.game.OnlineGame
import com.sejapoe.chess.game.board.DisplayBoard
import com.sejapoe.chess.game.piece.core.PieceColor

class OnlineGameActivity : ServerListenerActivity(), IBoardHolder {
    override val boardIds: MutableList<MutableList<Int>> = MutableList(8) { MutableList(8) { 0 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        DisplayBoard.generateBoard(this)

        if (intent.extras != null) {
            serverListener = OnlineGame(
                this,
                dTheme,
                intent.extras?.getLong("id") ?: -1,
                (intent.extras?.getSerializable("color") as? PieceColor) ?: PieceColor.WHITE
            ).also { it.restart() }
        }
    }
}