package ru.simple.notes.components.presentation.item

data class ItemState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)