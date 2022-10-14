package com.sejapoe.chess

import android.os.Bundle
import android.widget.Button
import com.sejapoe.chess.game.Game
import com.sejapoe.chess.game.theme.Theme

class GameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val game = Game(this, Theme.DEFAULT)

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            game.restart()
        }
        game.restart()
    }
}