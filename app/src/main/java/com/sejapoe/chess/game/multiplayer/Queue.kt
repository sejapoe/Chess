package com.sejapoe.chess.game.multiplayer

import android.app.Activity
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.widget.ProgressBar
import com.sejapoe.chess.OnlineGameActivity
import com.sejapoe.chess.QueueActivity
import com.sejapoe.chess.R
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Queue(activity: Activity) {
    val spinner: ProgressBar = activity.findViewById(R.id.queueSpinner)
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    var id: Long = -1L
    var gameId: Long = -1L

    init {
        CoroutineScope(Dispatchers.Default).launch {
            while (id == -1L) {
                try {
                    httpClient.request {
                        url("http://192.168.0.15:8080/registerMeToQueue")
                    }.run {
                        id = body<User>().id
                    }
                } catch (e: Exception) {
                    setSpinnerColor(Color.RED)
                }
            }
            setSpinnerColor(Color.GREEN)
            while (id != -1L) {
                delay(1000)
                try {
                    httpClient.request {
                        url("http://192.168.0.15:8080/matchmakingStatus/$id")
                    }.run {
                        // TODO: add 404 check
                        when (status) {
                            HttpStatusCode.OK -> setSpinnerColor(Color.GREEN)
                            HttpStatusCode.Created -> {
                                id = -1L
                                gameId = body()
                            }
                        }
                    }
                } catch (e: Exception) {
                    setSpinnerColor(Color.RED)
                }
            }
            if (gameId != -1L) {
                if (activity is QueueActivity) {
                    val intent = Intent(activity, OnlineGameActivity::class.java)
                    intent.putExtra("id", gameId)
                    activity.startActivity(intent)
                }
            }
            activity.finish()
        }
    }

    private fun setSpinnerColor(color: Int) {
        spinner.currentDrawable?.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    }
}