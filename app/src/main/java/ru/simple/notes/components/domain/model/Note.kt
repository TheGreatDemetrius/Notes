package ru.simple.notes.components.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.simple.notes.ui.theme.*

@Entity
data class Note(
    val title: String,
    val description: String,
    val date: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val colors = listOf(Orange, Yellow, Violet, Blue, Pink)
    }
}