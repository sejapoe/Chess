package com.sejapoe.chess.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.sejapoe.chess.R
import com.sejapoe.chess.game.board.DisplayBoard
import com.sejapoe.chess.game.theme.Theme
import com.sejapoe.chess.ui.core.BaseActivity
import com.sejapoe.chess.ui.core.IBoardHolder

class ThemeChooseActivity : BaseActivity(), IBoardHolder {
    override val boardIds: MutableList<MutableList<Int>> = MutableList(8) { MutableList(8) { 0 } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_choose)

        DisplayBoard.generateBoard(this)

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