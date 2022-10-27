package com.sejapoe.chess

import android.os.Bundle
import android.widget.Button
import com.sejapoe.chess.game.Game
import com.sejapoe.chess.game.board.DisplayBoard

class GameActivity : BaseActivity(), IBoardHolder {
    override val boardIds: MutableList<MutableList<Int>> = MutableList(8) { MutableList(8) { 0 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        DisplayBoard.generateBoard(this)

        val game = Game(this, dTheme)

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            game.restart()
        }
        game.restart()
    }
}