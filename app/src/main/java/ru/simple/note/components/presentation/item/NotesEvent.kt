package ru.simple.note.components.presentation.item

import ru.simple.note.components.domain.model.Note
import ru.simple.note.components.domain.util.OrderProperties

sealed class NotesEvent{
    data class Order(val orderProperties: OrderProperties): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
