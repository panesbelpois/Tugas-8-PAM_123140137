package com.newsreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsreader.data.local.SettingsManager
import com.newsreader.data.model.UiState
import com.newsreader.data.repository.NoteRepository
import com.newsreader.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // ── Offline First UI State for Notes ─────────────────────────────────
    val notesState: StateFlow<UiState<List<Note>>> = combine(
        settingsManager.getSortOrder(),
        _searchQuery
    ) { sortOrder, query ->
        Pair(sortOrder, query)
    }.flatMapLatest { (isNewestFirst, query) ->
        val flow = if (query.isBlank()) {
            repository.getAllNotes(isNewestFirst)
        } else {
            repository.searchNotes(query)
        }

        flow.map { notes ->
            if (notes.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(notes)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    fun searchNotes(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(title, content)
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            repository.updateNote(id, title, content)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }
}
