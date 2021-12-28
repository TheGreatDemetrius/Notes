package ru.simple.note.components.presentation.item

import ru.simple.note.components.domain.model.Note
import ru.simple.note.components.domain.util.OrderProperties
import ru.simple.note.components.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val orderProperties: OrderProperties = OrderProperties.Date(OrderType.Descending),
    val isOderSectionVisible: Boolean = false
)