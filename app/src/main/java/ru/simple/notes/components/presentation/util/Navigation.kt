package ru.simple.notes.components.presentation.util

sealed class Navigation(val route: String){
    object ItemScreen : Navigation("item_screen")
    object ListScreen : Navigation("list_screen")
}