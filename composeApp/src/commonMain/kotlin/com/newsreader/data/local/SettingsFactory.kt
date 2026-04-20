package com.newsreader.data.local

import com.russhwolf.settings.Settings

expect class SettingsFactory {
    fun createSettings(): Settings
}
