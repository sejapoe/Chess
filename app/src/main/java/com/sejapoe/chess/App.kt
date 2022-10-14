package com.sejapoe.chess

import android.app.Application
import android.content.Context
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
        BaseActivity.dLocale = sharedPreferences.getString("language", "en")?.let { Locale(it) } ?: Locale.ENGLISH
        BaseActivity.settingsEditor = sharedPreferences.edit()
    }
}