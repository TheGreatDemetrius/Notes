package ru.simple.notes.components.domain.usecase

import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.domain.repository.NoteRepository
import java.lang.Exception

class AddNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank())
            throw IllegalArgumentException()
        repository.addNote(note)
    }
}