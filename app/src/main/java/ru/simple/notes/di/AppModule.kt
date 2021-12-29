package ru.simple.notes.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.simple.notes.components.data.source.NoteDatabase
import ru.simple.notes.components.data.storage.NoteStorage
import ru.simple.notes.components.domain.repository.NoteRepository
import ru.simple.notes.components.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase =
        Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository =
        NoteStorage(db.noteDao)

    @Provides
    @Singleton
    fun provideNote(repository: NoteRepository): ActionsNote =
        ActionsNote(
            GetAllNotes(repository),
            DeleteNote(repository),
            AddNote(repository),
            GetNote(repository)
        )
}