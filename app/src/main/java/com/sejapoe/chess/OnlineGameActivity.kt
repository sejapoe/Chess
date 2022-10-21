package com.sejapoe.chess

import android.os.Bundle
import com.sejapoe.chess.game.OnlineGame
import com.sejapoe.chess.game.piece.core.PieceColor

class OnlineGameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        if (intent.extras != null) {
            val game = OnlineGame(
                this,
                dTheme,
                intent.extras?.getLong("id") ?: -1,
                (intent.extras?.getSerializable("color") as? PieceColor) ?: PieceColor.WHITE
            )
            game.restart()
        }
    }
}