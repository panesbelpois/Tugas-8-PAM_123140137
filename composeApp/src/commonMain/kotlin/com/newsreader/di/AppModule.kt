package com.newsreader.di

import com.newsreader.data.local.SettingsManager
import com.newsreader.data.local.datasource.NoteLocalDataSource
import com.newsreader.data.repository.NoteRepository
import com.newsreader.database.AppDatabase
import com.newsreader.platform.BatteryInfo
import com.newsreader.platform.DeviceInfo
import com.newsreader.platform.NetworkMonitor
import com.newsreader.presentation.viewmodel.NotesViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// Platform-specific module providing DatabaseDriverFactory and SettingsFactory
expect fun platformModule(): Module

val appModules = module {

    // ── Data ─────────────────────────────────────────────────────────────
    single { AppDatabase(get<com.newsreader.database.DatabaseDriverFactory>().createDriver()) }
    single { NoteLocalDataSource(get()) }
    single { NoteRepository(get()) }
    single { SettingsManager(get<com.newsreader.data.local.SettingsFactory>().createSettings()) }

    // ── Platform ─────────────────────────────────────────────────────────
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }

    // ── ViewModel ────────────────────────────────────────────────────────
    factory { NotesViewModel(get(), get()) }
}
