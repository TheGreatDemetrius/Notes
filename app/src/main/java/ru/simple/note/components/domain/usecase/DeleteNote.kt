package ru.simple.note.components.domain.usecase

import ru.simple.note.components.domain.model.Note
import ru.simple.note.components.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}