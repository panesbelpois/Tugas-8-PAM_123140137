package com.newsreader.data.repository

import com.newsreader.data.local.datasource.NoteLocalDataSource
import com.newsreader.database.NoteEntity
import com.newsreader.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val localDataSource: NoteLocalDataSource
) {
    fun getAllNotes(isNewestFirst: Boolean): Flow<List<Note>> {
        val entityFlow = if (isNewestFirst) {
            localDataSource.getAllNotesDescending()
        } else {
            localDataSource.getAllNotesAscending()
        }
        
        return entityFlow.map { entities ->
            entities.map { it.toDomainNote() }
        }
    }

    fun getNoteById(id: Long): Flow<Note?> {
        return localDataSource.getNoteById(id).map { it?.toDomainNote() }
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return localDataSource.searchNotes(query).map { entities ->
            entities.map { it.toDomainNote() }
        }
    }

    suspend fun insertNote(title: String, content: String) {
        val timestamp = System.currentTimeMillis()
        localDataSource.insertNote(title, content, timestamp, timestamp)
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        val timestamp = System.currentTimeMillis()
        localDataSource.updateNote(id, title, content, timestamp)
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    private fun NoteEntity.toDomainNote(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            createdAt = created_at,
            updatedAt = updated_at
        )
    }
}
