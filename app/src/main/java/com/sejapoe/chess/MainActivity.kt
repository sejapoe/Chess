package com.sejapoe.chess

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sejapoe.chess.game.board.Board

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = Board(this)

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            board.resetSetup()
        }
        board.resetSetup()
    }
}