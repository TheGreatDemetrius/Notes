package ru.simple.notes.components.domain.usecase

import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}