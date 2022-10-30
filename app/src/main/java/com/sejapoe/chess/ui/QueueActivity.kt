package com.sejapoe.chess.ui

import android.os.Bundle
import com.sejapoe.chess.R
import com.sejapoe.chess.game.multiplayer.Queue
import com.sejapoe.chess.ui.core.ServerListenerActivity

class QueueActivity : ServerListenerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)

        serverListener = Queue(this)
    }
}
