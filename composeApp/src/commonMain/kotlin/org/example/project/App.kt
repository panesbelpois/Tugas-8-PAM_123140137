package org.example.project

import androidx.compose.runtime.Composable
import com.newsreader.MainApp
import com.newsreader.data.local.SettingsFactory
import com.newsreader.database.DatabaseDriverFactory

@Composable
fun App(
    databaseDriverFactory: DatabaseDriverFactory,
    settingsFactory: SettingsFactory
) {
    MainApp(databaseDriverFactory, settingsFactory)
}