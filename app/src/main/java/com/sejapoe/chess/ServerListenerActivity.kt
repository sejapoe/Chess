package com.sejapoe.chess

import com.sejapoe.chess.game.multiplayer.IServerListener

open class ServerListenerActivity : BaseActivity() {
    protected var serverListener: IServerListener? = null

    override fun onDestroy() {
        super.onDestroy()
        serverListener?.job?.cancel()
    }
}
