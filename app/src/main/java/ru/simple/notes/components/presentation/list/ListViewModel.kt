package ru.simple.notes.components.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.domain.usecase.ActionsNote
import ru.simple.notes.components.domain.util.OrderProperties
import ru.simple.notes.components.domain.util.OrderType
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val actionsNote: ActionsNote
) : ViewModel() {

    private val _state = mutableStateOf(ListState())
    val state: State<ListState> = _state
    private var recentlyDeleteNote: Note? = null
    private var getAllNotesJob: Job? = null

    init {
        getAllNotes(OrderProperties.Date(OrderType.Descending))
    }

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.Order ->
                if (state.value.orderProperties::class == event.orderProperties::class &&
                    state.value.orderProperties.orderType == event.orderProperties.orderType
                ) return
                else getAllNotes(event.orderProperties)
            is ListEvent.DeleteNote ->
                viewModelScope.launch {
                    actionsNote.deleteNote(event.note)
                    recentlyDeleteNote = event.note
                }
            is ListEvent.RestoreNote ->
                viewModelScope.launch {
                    actionsNote.addNote(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            is ListEvent.ToggleOrderSection ->
                _state.value =
                    state.value.copy(isOderSectionVisible = !state.value.isOderSectionVisible)
        }
    }

    private fun getAllNotes(orderProperties: OrderProperties) {
        getAllNotesJob?.cancel()
        getAllNotesJob = actionsNote.getAllNotes(orderProperties)
            .onEach { notes ->
                _state.value = state.value.copy(notes = notes, orderProperties = orderProperties)
            }.launchIn(viewModelScope)
    }
}