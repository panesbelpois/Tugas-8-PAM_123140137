package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import com.newsreader.data.local.SettingsFactory
import com.newsreader.database.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController { 
    App(
        databaseDriverFactory = DatabaseDriverFactory(),
        settingsFactory = SettingsFactory()
    ) 
}