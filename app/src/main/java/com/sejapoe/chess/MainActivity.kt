package com.sejapoe.chess

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private val makeSelected = View.OnClickListener {
        val currentColor = (it.background as ColorDrawable).color.plus(0x1000000)
        it.setBackgroundColor(
            when (it.tag) {
                "black" -> if (currentColor == 0x000000) 0xFF7E4A00.toInt() else 0xFF000000.toInt()
                "white" -> if (currentColor == 0xFFFFFF) 0xFFFF9805.toInt() else 0xFFFFFFFF.toInt()
                else -> throw IllegalStateException("Cell has wrong tag")
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (c in 'a'..'h') {
            for (i in 1..8) {
                val id = resources.getIdentifier("$c$i", "id", packageName)
                val cell = findViewById<ImageView>(id)
                cell.setOnClickListener(this.makeSelected)
            }
        }
    }
}