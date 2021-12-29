package ru.simple.notes.components.domain.usecase

import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.domain.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note =
        repository.getNote(id)
}