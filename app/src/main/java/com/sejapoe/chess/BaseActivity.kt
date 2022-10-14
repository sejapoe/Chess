package com.sejapoe.chess

import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import java.util.*

open class BaseActivity : AppCompatActivity() {
    init {
        updateConfig(this)
    }

    private fun updateConfig(baseActivity: BaseActivity) {
        if (dLocale == Locale(""))
            return

        Locale.setDefault(dLocale)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        baseActivity.applyOverrideConfiguration(configuration)
    }

    companion object {
        lateinit var settingsEditor: SharedPreferences.Editor
        lateinit var dLocale: Locale
    }
}