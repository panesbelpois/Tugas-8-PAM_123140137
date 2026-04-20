package com.newsreader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newsreader.presentation.components.BottomNavBar
import com.newsreader.presentation.components.BottomNavItem
import com.newsreader.presentation.notes.*
import com.newsreader.presentation.settings.SettingsScreen
import com.newsreader.ui.theme.NewsReaderTheme
import com.newsreader.domain.model.Note
import androidx.compose.runtime.saveable.rememberSaveable
import com.newsreader.presentation.viewmodel.NotesViewModel
import com.newsreader.database.DatabaseDriverFactory
import com.newsreader.data.local.SettingsFactory
import com.newsreader.database.AppDatabase
import com.newsreader.data.local.datasource.NoteLocalDataSource
import com.newsreader.data.repository.NoteRepository
import com.newsreader.data.local.SettingsManager
import kotlinx.coroutines.launch

@Composable
fun MainApp(
    databaseDriverFactory: DatabaseDriverFactory,
    settingsFactory: SettingsFactory
) {
    // ── Offline First Dependencies ──────────────────────────────────────────
    val database = remember { AppDatabase(databaseDriverFactory.createDriver()) }
    val localDataSource = remember { NoteLocalDataSource(database) }
    
    val repository = remember { NoteRepository(localDataSource) }
    val settingsManager = remember { SettingsManager(settingsFactory.createSettings()) }

    // ── ViewModel ─────────────────────────────────────────────────────────
    val viewModel: NotesViewModel = viewModel {
        NotesViewModel(repository, settingsManager)
    }

    val scope = rememberCoroutineScope()

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
        var selectedNote    by remember { mutableStateOf<Note?>(null) } // Used for both viewing and editing (if viewing)
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
                    currentRoute = BottomNavItem.Saved.route // Jump to Edit mode
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
                            selectedNote = null // Ready to create a new note
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