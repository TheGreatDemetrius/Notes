package ru.simple.note.components.presentation.unit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.simple.note.components.domain.model.NoteModel
import ru.simple.note.components.domain.usecase.ActionsNote
import ru.simple.note.components.domain.util.OrderProperties
import ru.simple.note.components.domain.util.OrderType
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val actionsNote: ActionsNote
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    private val state: State<NotesState> = _state
    private var recentlyDeleteNote: NoteModel? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(OrderProperties.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order ->
                if (state.value.orderProperties::class == event.orderProperties::class &&
                    state.value.orderProperties.orderType == event.orderProperties.orderType
                ) return
                else getNotes(event.orderProperties)
            is NotesEvent.DeleteNote ->
                viewModelScope.launch {
                    actionsNote.deleteNote(event.note)
                    recentlyDeleteNote = event.note
                }
            is NotesEvent.RestoreNote ->
                viewModelScope.launch {
                    actionsNote.addNote(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            is NotesEvent.ToggleOrderSection ->
                _state.value =
                    state.value.copy(isOderSectionVisible = !state.value.isOderSectionVisible)
        }
    }

    private fun getNotes(orderProperties: OrderProperties) {
        getNotesJob?.cancel()
        getNotesJob = actionsNote.getNotes(orderProperties)
            .onEach { notes ->
                _state.value = state.value.copy(notes = notes, orderProperties = orderProperties)
            }.launchIn(viewModelScope)
    }
}