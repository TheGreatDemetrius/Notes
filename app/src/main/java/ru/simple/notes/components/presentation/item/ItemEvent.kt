package ru.simple.notes.components.presentation.item

import androidx.compose.ui.focus.FocusState

sealed class ItemEvent {
    data class EnteredTitle(val value: String) : ItemEvent()
    data class ChangeTitleFocus(val focus: FocusState) : ItemEvent()
    data class EnteredDescription(val value: String) : ItemEvent()
    data class ChangeDescriptionFocus(val focus: FocusState) : ItemEvent()
    data class ChangeColor(val color: Int) : ItemEvent()
    object SaveNote : ItemEvent()
}