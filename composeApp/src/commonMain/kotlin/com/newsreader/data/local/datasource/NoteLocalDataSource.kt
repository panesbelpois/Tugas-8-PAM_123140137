package com.newsreader.data.local.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.newsreader.database.AppDatabase
import com.newsreader.database.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.IO

class NoteLocalDataSource(database: AppDatabase) {
    private val queries = database.noteQueries

    fun getAllNotesDescending(): Flow<List<NoteEntity>> {
        return queries.selectAll().asFlow().mapToList(Dispatchers.IO)
    }

    fun getAllNotesAscending(): Flow<List<NoteEntity>> {
        return queries.selectAllAscending().asFlow().mapToList(Dispatchers.IO)
    }

    fun getNoteById(id: Long): Flow<NoteEntity?> {
        return queries.selectById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return queries.search(query).asFlow().mapToList(Dispatchers.IO)
    }

    suspend fun insertNote(title: String, content: String, createdAt: Long, updatedAt: Long) = kotlinx.coroutines.withContext(Dispatchers.IO) {
        queries.insert(title, content, createdAt, updatedAt)
    }

    suspend fun updateNote(id: Long, title: String, content: String, updatedAt: Long) = kotlinx.coroutines.withContext(Dispatchers.IO) {
        queries.update(title, content, updatedAt, id)
    }

    suspend fun deleteNote(id: Long) = kotlinx.coroutines.withContext(Dispatchers.IO) {
        queries.delete(id)
    }
}
