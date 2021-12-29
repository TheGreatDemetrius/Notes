package ru.simple.notes.components.domain.usecase

data class ActionsNote(
    val getAllNotes: GetAllNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
)