package ru.simple.note.components.presentation.unit

import ru.simple.note.components.domain.model.NoteModel
import ru.simple.note.components.domain.util.OrderProperties

sealed class NotesEvent{
    data class Order(val orderProperties: OrderProperties): NotesEvent()
    data class DeleteNote(val note: NoteModel): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
