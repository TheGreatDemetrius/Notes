package ru.simple.note.components.data.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.simple.note.components.domain.model.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<NoteModel>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int) : NoteModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)//зачем тащить всю задачу? если можно тащить только идентификатор
}