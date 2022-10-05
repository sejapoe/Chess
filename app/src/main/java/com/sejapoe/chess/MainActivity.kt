package com.sejapoe.chess

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.sejapoe.chess.game.board.Board
import com.sejapoe.chess.game.board.Cell

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = Board(MutableList(8) {
            val row = mutableListOf<Cell>()
            for (i in 1..8) {
                val textId = "${'a' + it}$i"
                val id = resources.getIdentifier(textId, "id", packageName)
                val cell = findViewById<ImageView>(id)
                row.add(Cell(cell, textId))
            }
            row
        })

        for (cell in board.cells.flatten()) {
            cell.setOnClickListener {
                if (it.piece != null) {
                    it.toggleSelection()
                }
            }
        }



        board.resetSetup()
    }
}