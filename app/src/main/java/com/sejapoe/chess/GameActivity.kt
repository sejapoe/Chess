package com.sejapoe.chess

import android.os.Bundle
import android.widget.Button
import com.sejapoe.chess.game.Game

class GameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val game = Game(this, dTheme)

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            game.restart()
        }
        game.restart()
    }
}