package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.newsreader.data.local.SettingsFactory
import com.newsreader.database.DatabaseDriverFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "anisahpam6",
    ) {
        App(
            databaseDriverFactory = DatabaseDriverFactory(),
            settingsFactory = SettingsFactory()
        )
    }
}