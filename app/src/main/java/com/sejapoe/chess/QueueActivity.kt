package com.sejapoe.chess

import android.os.Bundle
import com.sejapoe.chess.game.multiplayer.Queue

class QueueActivity : ServerListenerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)

        serverListener = Queue(this)
    }
}
