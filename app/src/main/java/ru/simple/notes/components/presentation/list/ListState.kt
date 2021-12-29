package ru.simple.notes.components.presentation.list

import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.domain.util.OrderProperties
import ru.simple.notes.components.domain.util.OrderType

data class ListState(
    val notes: List<Note> = emptyList(),
    val orderProperties: OrderProperties = OrderProperties.Date(OrderType.Descending),
    val isOderSectionVisible: Boolean = false
)