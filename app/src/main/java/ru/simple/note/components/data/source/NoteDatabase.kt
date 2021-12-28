package ru.simple.note.components.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.simple.note.components.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}