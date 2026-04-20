package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.newsreader.data.local.SettingsFactory
import com.newsreader.database.DatabaseDriverFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App(
                databaseDriverFactory = DatabaseDriverFactory(this),
                settingsFactory = SettingsFactory(this)
            )
        }
    }
}