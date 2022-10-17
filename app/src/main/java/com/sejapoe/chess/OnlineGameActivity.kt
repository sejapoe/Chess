package com.sejapoe.chess

import android.os.Bundle
import android.widget.Button
import com.sejapoe.chess.game.OnlineGame
import com.sejapoe.chess.game.piece.core.PieceColor

class OnlineGameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        if (savedInstanceState != null) {
            val game = OnlineGame(this, dTheme, savedInstanceState.getLong("id"), PieceColor.WHITE)

            findViewById<Button>(R.id.resetButton).setOnClickListener {
                game.restart()
            }
            game.restart()
        }
    }
}