package ru.simple.note.components.domain.usecase

data class ActionsNote(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote
)