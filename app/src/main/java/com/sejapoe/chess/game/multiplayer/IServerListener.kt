package com.sejapoe.chess.game.multiplayer

import kotlinx.coroutines.Job

interface IServerListener {
    var job: Job
}
