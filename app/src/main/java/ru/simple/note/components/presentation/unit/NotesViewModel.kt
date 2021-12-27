package ru.simple.note.components.presentation.unit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.simple.note.components.domain.usecase.ComponentsNote
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
private val componentsNote: ComponentsNote
): ViewModel(){

}