package com.newsreader.di

import com.newsreader.data.local.SettingsFactory
import com.newsreader.database.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { SettingsFactory() }
}
