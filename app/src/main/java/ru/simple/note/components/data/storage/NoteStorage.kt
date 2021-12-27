package ru.simple.note.components.data.storage

import kotlinx.coroutines.flow.Flow
import ru.simple.note.components.data.source.NoteDao
import ru.simple.note.components.domain.model.NoteModel
import ru.simple.note.components.domain.repository.NoteRepository

class NoteStorage(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<NoteModel>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): NoteModel {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: NoteModel) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: NoteModel) {
        dao.deleteNote(note)
    }
}