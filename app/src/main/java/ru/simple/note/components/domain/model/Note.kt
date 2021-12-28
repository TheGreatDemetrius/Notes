package ru.simple.note.components.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.simple.note.ui.theme.*
import java.lang.Exception

@Entity
data class Note(
    val title: String,
    val description: String,
    val date: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColor = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)