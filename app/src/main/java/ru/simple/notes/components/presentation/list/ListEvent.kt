package ru.simple.notes.components.presentation.list

import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.domain.util.OrderProperties

sealed class ListEvent{
    data class Order(val orderProperties: OrderProperties): ListEvent()
    data class DeleteNote(val note: Note): ListEvent()
    object RestoreNote: ListEvent()
    object ToggleOrderSection: ListEvent()
}
