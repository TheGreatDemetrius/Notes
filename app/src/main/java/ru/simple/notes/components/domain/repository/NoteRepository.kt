package ru.simple.notes.components.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.simple.notes.components.domain.model.Note

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNote(id: Int) : Note

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)
}