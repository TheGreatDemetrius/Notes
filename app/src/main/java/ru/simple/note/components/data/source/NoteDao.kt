package ru.simple.note.components.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.simple.note.components.domain.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int) : Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)//зачем тащить всю задачу? если можно тащить только идентификатор
}