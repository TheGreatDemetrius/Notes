package ru.simple.notes.components.data.storage

import kotlinx.coroutines.flow.Flow
import ru.simple.notes.components.data.source.NoteDao
import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.domain.repository.NoteRepository

class NoteStorage(
    private val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }

    override suspend fun getNote(id: Int): Note {
        return dao.getNote(id)
    }

    override suspend fun addNote(note: Note) {
        dao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}