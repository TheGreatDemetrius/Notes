package ru.simple.note.components.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.simple.note.components.domain.model.NoteModel

interface NoteRepository {
    fun getNotes(): Flow<List<NoteModel>>

    suspend fun getNoteById(id: Int) : NoteModel

    suspend fun insertNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)
}