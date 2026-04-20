package com.newsreader.data.local

import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsFactory(private val context: Context) {
    actual fun createSettings(): Settings {
        val delegate: SharedPreferences = context.getSharedPreferences("newsreader_settings", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}
