package com.sejapoe.chess

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sejapoe.chess.game.theme.Theme

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.startBtn).setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("theme", Theme.DEFAULT)
            startActivity(intent)
        }
    }
}