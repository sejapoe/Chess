package com.sejapoe.chess

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.sejapoe.chess.game.board.DisplayBoard
import com.sejapoe.chess.game.theme.Theme

class ThemeChooseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_choose)

        val textColor: TextView = findViewById(R.id.themeColor)
        val textRes: TextView = findViewById(R.id.themeRes)
        textColor.text = dTheme.colors.name
        textRes.text = dTheme.resources.name
        DisplayBoard(this, dTheme).resetSetup()

        findViewById<Button>(R.id.nextColorBtn).setOnClickListener {
            dTheme = Theme(dTheme.colors + 1, dTheme.resources)
            textColor.text = dTheme.colors.name
            DisplayBoard(this, dTheme).resetSetup()
        }

        findViewById<Button>(R.id.nextResBtn).setOnClickListener {
            dTheme = Theme(dTheme.colors, dTheme.resources + 1)
            textRes.text = dTheme.resources.name
            DisplayBoard(this, dTheme).resetSetup()
        }

        findViewById<Button>(R.id.prevColorBtn).setOnClickListener {
            dTheme = Theme(dTheme.colors - 1, dTheme.resources)
            textColor.text = dTheme.colors.name
            DisplayBoard(this, dTheme).resetSetup()
        }

        findViewById<Button>(R.id.prevResBtn).setOnClickListener {
            dTheme = Theme(dTheme.colors, dTheme.resources - 1)
            textRes.text = dTheme.resources.name
            DisplayBoard(this, dTheme).resetSetup()
        }
    }
}