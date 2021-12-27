package ru.simple.note.components.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.simple.note.components.domain.model.Note
import ru.simple.note.components.domain.repository.NoteRepository
import ru.simple.note.components.domain.util.OrderProperties
import ru.simple.note.components.domain.util.OrderType

class GetNotes(//классы вариантов использования должны содержать только одну публичную функцию
    private val repository: NoteRepository
) {
    operator fun invoke(
        orderProperties: OrderProperties = OrderProperties.Date(OrderType.Descending)
    ): Flow<List<Note>> = repository.getNotes().map { notes ->
        if (orderProperties.orderType == OrderType.Ascending)
            when (orderProperties) {
                is OrderProperties.Title -> notes.sortedBy { note -> note.title.lowercase() }
                is OrderProperties.Date -> notes.sortedBy { note -> note.date }
                is OrderProperties.Color -> notes.sortedBy { note -> note.color }
            }
        else
            when (orderProperties) {
                is OrderProperties.Title -> notes.sortedByDescending { note -> note.title.lowercase() }
                is OrderProperties.Date -> notes.sortedByDescending { note -> note.date }
                is OrderProperties.Color -> notes.sortedByDescending { note -> note.color }
            }

    }
}