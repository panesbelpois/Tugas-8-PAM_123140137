package com.newsreader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newsreader.presentation.components.BottomNavBar
import com.newsreader.presentation.components.BottomNavItem
import com.newsreader.presentation.notes.*
import com.newsreader.presentation.settings.SettingsScreen
import com.newsreader.ui.theme.NewsReaderTheme
import com.newsreader.domain.model.Note
import androidx.compose.runtime.saveable.rememberSaveable
import com.newsreader.presentation.viewmodel.NotesViewModel
import com.newsreader.data.local.SettingsFactory
import com.newsreader.data.local.SettingsManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun MainApp(
    databaseDriverFactory: com.newsreader.database.DatabaseDriverFactory? = null,
    settingsFactory: SettingsFactory? = null
) {
    // ── ViewModel injected via Koin ───────────────────────────────────────
    val viewModel: NotesViewModel = koinInject()

    val scope = rememberCoroutineScope()

    // ── Settings (still resolved via Koin-managed SettingsManager) ────────
    // Access SettingsManager from Koin to collect settings flows
    val settingsManager: SettingsManager = org.koin.compose.koinInject()

    // ── Collect StateFlows ────────────────────────────────────────────────
    val notesState   by viewModel.notesState.collectAsStateWithLifecycle()
    val currentTheme  by settingsManager.getTheme().collectAsStateWithLifecycle(0)
    val isNewestFirst by settingsManager.getSortOrder().collectAsStateWithLifecycle(true)

    val isSystemDark = androidx.compose.foundation.isSystemInDarkTheme()
    val isDark = when (currentTheme) {
        1 -> false
        2 -> true
        else -> isSystemDark
    }

    NewsReaderTheme(darkTheme = isDark) {
        // ── Navigation state ──────────────────────────────────────────────────
        var currentRoute    by rememberSaveable { mutableStateOf(BottomNavItem.Home.route) }
        var selectedNote    by remember { mutableStateOf<Note?>(null) }
        var isViewingNote   by remember { mutableStateOf(false) }

        // ── Details Overlay (View Single Note) ───────────────────────────────────
        if (isViewingNote && selectedNote != null) {
            DetailScreen(
                note     = selectedNote!!,
                onBack   = { 
                    isViewingNote = false
                    selectedNote = null
                },
                onEdit   = {
                    isViewingNote = false
                    currentRoute = BottomNavItem.Saved.route
                },
                onDelete = {
                    viewModel.deleteNote(selectedNote!!.id)
                    isViewingNote = false
                    selectedNote = null
                }
            )
            return@NewsReaderTheme
        }

        Scaffold(
            bottomBar = {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate   = { 
                        if (it == BottomNavItem.Saved.route && currentRoute != BottomNavItem.Saved.route) {
                            selectedNote = null
                        }
                        currentRoute = it 
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentRoute) {

                    BottomNavItem.Home.route -> HomeScreen(
                        uiState      = notesState,
                        onNoteClick  = {
                            selectedNote = it
                            isViewingNote = true
                        },
                        onSearch     = { viewModel.searchNotes(it) }
                    )

                    BottomNavItem.Saved.route -> EditScreen(
                        note   = selectedNote,
                        onSave = { title, content ->
                            if (selectedNote == null) {
                                viewModel.addNote(title, content)
                            } else {
                                viewModel.updateNote(selectedNote!!.id, title, content)
                            }
                            currentRoute = BottomNavItem.Home.route
                        },
                        onBack = { currentRoute = BottomNavItem.Home.route }
                    )

                    BottomNavItem.Settings.route -> SettingsScreen(
                        currentTheme  = currentTheme,
                        isNewestFirst = isNewestFirst,
                        onThemeChange = { scope.launch { settingsManager.setTheme(it) } },
                        onSortChange  = { scope.launch { settingsManager.setSortOrder(it) } },
                        onBack        = { currentRoute = BottomNavItem.Home.route }
                    )
                }
            }
        }
    }
}