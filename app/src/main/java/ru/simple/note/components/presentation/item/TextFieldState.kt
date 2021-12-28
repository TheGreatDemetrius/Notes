package ru.simple.note.components.presentation.item

data class TextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)