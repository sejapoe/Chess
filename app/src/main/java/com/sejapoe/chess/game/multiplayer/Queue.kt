package com.sejapoe.chess.game.multiplayer

import android.app.Activity
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.ProgressBar
import com.sejapoe.chess.App
import com.sejapoe.chess.OnlineGameActivity
import com.sejapoe.chess.QueueActivity
import com.sejapoe.chess.R
import io.ktor.client.*
import io.ktor.client.call.*
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
    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }
    var id: Long = -1L

    init {
        CoroutineScope(Dispatchers.Default).launch {
            while (id == -1L) {
                try {
                    httpClient.request {
                        url("${App.HOST}/registerMeToQueue")
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
                        url("${App.HOST}/matchmakingStatus/$id")
                    }.run {
                        // TODO: add 404 check
                        when (status) {
                            HttpStatusCode.OK -> setSpinnerColor(Color.GREEN)
                            HttpStatusCode.Created -> {
                                id = -1L
                                val gameData: GameCreatingData = body()
                                if (activity is QueueActivity) {
                                    val intent = Intent(activity, OnlineGameActivity::class.java)
                                    intent.putExtra("id", gameData.id)
                                    intent.putExtra("color", gameData.yourColor)
                                    activity.startActivity(intent)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    setSpinnerColor(Color.RED)
                }
            }
            activity.finish()
        }
    }

    private fun setSpinnerColor(color: Int) {
        if (VERSION.SDK_INT >= VERSION_CODES.Q) spinner.currentDrawable?.colorFilter =
            BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    }
}