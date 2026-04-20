package com.newsreader.data.local

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsManager(private val settings: Settings) {

    private val _theme = MutableStateFlow(settings.getInt("THEME_PREF", 0))
    private val _sortOrder = MutableStateFlow(settings.getBoolean("SORT_ORDER", true))

    suspend fun setTheme(themeIndex: Int) {
        settings.putInt("THEME_PREF", themeIndex)
        _theme.value = themeIndex
    }

    fun getTheme(): StateFlow<Int> {
        // 0 = System, 1 = Light, 2 = Dark
        return _theme.asStateFlow()
    }

    suspend fun setSortOrder(isNewestFirst: Boolean) {
        settings.putBoolean("SORT_ORDER", isNewestFirst)
        _sortOrder.value = isNewestFirst
    }

    fun getSortOrder(): StateFlow<Boolean> {
        return _sortOrder.asStateFlow()
    }
}
