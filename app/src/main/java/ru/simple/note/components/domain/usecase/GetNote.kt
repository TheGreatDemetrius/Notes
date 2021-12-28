package ru.simple.note.components.domain.usecase

import ru.simple.note.components.domain.model.Note
import ru.simple.note.components.domain.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note =
        repository.getNoteById(id)
}