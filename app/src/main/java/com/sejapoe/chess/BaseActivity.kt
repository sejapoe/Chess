package com.sejapoe.chess

import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.sejapoe.chess.game.theme.Theme
import java.util.*
import java.util.logging.Logger

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
        var dLocale: Locale = Locale.ENGLISH
            set(value) {
                settingsEditor.putString("language", value.language)
                settingsEditor.commit()
                field = value
            }
        var dTheme: Theme = Theme.DEFAULT
            set(value) {
                settingsEditor.putString("theme", "${value.colors.name}/${value.resources.name}")
                Logger.getGlobal().info("${value.colors.name}/${value.resources.name}")
                settingsEditor.commit()
                field = value
            }
    }
}