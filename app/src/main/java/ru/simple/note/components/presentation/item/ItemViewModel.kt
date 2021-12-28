package ru.simple.note.components.presentation.item

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.simple.note.R
import ru.simple.note.components.domain.model.InvalidNoteException
import ru.simple.note.components.domain.model.Note
import ru.simple.note.components.domain.usecase.ActionsNote
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val actionsNote: ActionsNote,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val res = getApplication<Application>().resources
    private val _title = mutableStateOf(
        TextFieldState(
            hint = res.getString(R.string.enter_title)
        )
    )
    val title: State<TextFieldState> = _title

    private val _description = mutableStateOf(
        TextFieldState(
            hint = res.getString(R.string.enter_description)
        )
    )
    private val description: State<TextFieldState> = _description

    private val _color = mutableStateOf(Note.noteColor.random().toArgb())
    val color: State<Int> = _color

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    actionsNote.getNote(noteId).also { note ->
                        currentNoteId = note.id
                        _title.value = title.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _description.value = description.value.copy(
                            text = note.description,
                            isHintVisible = false
                        )
                        _color.value = note.color
                    }

                }
            }
        }
    }

    fun onEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.EnteredTitle ->
                _title.value = title.value.copy(
                    text = event.value
                )
            is ItemEvent.ChangeTitleFocus ->
                _title.value = title.value.copy(
                    isHintVisible = !event.focus.isFocused &&
                            title.value.text.isBlank()
                )
            is ItemEvent.EnteredDescription ->
                _description.value = description.value.copy(
                    text = event.value
                )
            is ItemEvent.ChangeDescriptionFocus ->
                _description.value = description.value.copy(
                    isHintVisible = !event.focus.isFocused &&
                            description.value.text.isBlank()
                )
            is ItemEvent.ChangeColor ->
                _color.value = event.color
            is ItemEvent.SaveNote ->
                viewModelScope.launch {
                    try {
                        actionsNote.addNote(
                            Note(
                                title = title.value.text,
                                description = description.value.text,
                                date = System.currentTimeMillis(),
                                color = color.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save note."
                            )
                        )
                    }
                }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}