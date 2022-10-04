package com.sejapoe.chess

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.sejapoe.chess.game.Cell

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cells = MutableList<MutableList<Cell>>(8) {
            val row = mutableListOf<Cell>()
            for (i in 1..8) {
                val textId = "${'a' + it}$i"
                val id = resources.getIdentifier(textId, "id", packageName)
                val cell = findViewById<ImageView>(id)
                row.add(Cell(cell, textId))
            }
            row
        }

        for (cell in cells.flatten()) {
            cell.setOnClickListener {
                it.toggleSelection()
            }
        }
    }
}