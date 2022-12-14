package com.sejapoe.chess

import android.app.Application
import android.content.Context
import com.sejapoe.chess.game.theme.Theme
import com.sejapoe.chess.game.theme.ThemeColors
import com.sejapoe.chess.game.theme.ThemeResources
import com.sejapoe.chess.ui.core.BaseActivity
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
        BaseActivity.settingsEditor = sharedPreferences.edit()
        BaseActivity.dLocale = sharedPreferences.getString("language", "en")?.let { Locale(it) } ?: Locale.ENGLISH
        BaseActivity.dTheme =
            sharedPreferences.getString("theme", "GREEN/TAKE_ONE")?.let {
                val strings = it.split("/")
                Theme(ThemeColors.valueOf(strings[0]), ThemeResources.valueOf(strings[1]))
            } ?: Theme.DEFAULT
    }

    companion object {
        const val HOST = "https://chess-srv.herokuapp.com" // prod
        //        const val HOST = "http://192.168.0.15:8080" // dev
    }
}