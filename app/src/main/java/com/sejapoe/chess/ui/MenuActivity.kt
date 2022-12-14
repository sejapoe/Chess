package com.sejapoe.chess.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.sejapoe.chess.R
import com.sejapoe.chess.ui.core.BaseActivity
import java.util.*


class MenuActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.startBtn).setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.startOnlineBtn).setOnClickListener {
            val intent = Intent(this, QueueActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.themeChooseBtn).setOnClickListener {
            val intent = Intent(this, ThemeChooseActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.enBtn).setOnClickListener {
            updateLanguage(Locale.ENGLISH)
        }

        findViewById<Button>(R.id.ruBtn).setOnClickListener {
            updateLanguage(Locale("ru"))
        }
    }

    private fun updateLanguage(locale: Locale) {
        dLocale = locale
        val intent = Intent(applicationContext, MenuActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}