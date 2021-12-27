package ru.simple.note.components.presentation.unit

import ru.simple.note.components.domain.model.NoteModel
import ru.simple.note.components.domain.util.OrderProperties
import ru.simple.note.components.domain.util.OrderType

data class NotesState(
    val notes: List<NoteModel> = emptyList(),
    val orderProperties: OrderProperties = OrderProperties.Date(OrderType.Descending),
    val isOderSectionVisible: Boolean = false
)