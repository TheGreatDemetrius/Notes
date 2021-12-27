package ru.simple.note.components.domain.usecase

import ru.simple.note.components.domain.model.InvalidNoteException
import ru.simple.note.components.domain.model.NoteModel
import ru.simple.note.components.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: NoteModel) {
        if (note.title.isBlank())
            throw InvalidNoteException("The title of the note can't be empty.")
        if (note.description.isBlank())
            throw InvalidNoteException("The description of the note can't be empty.")
        repository.insertNote(note)
    }
}