package com.sejapoe.chess.ui.core

import com.sejapoe.chess.game.multiplayer.IServerListener

abstract class ServerListenerActivity : BaseActivity() {
    protected var serverListener: IServerListener? = null

    override fun onDestroy() {
        super.onDestroy()
        serverListener?.job?.cancel()
    }
}
