package ru.simple.note.components.domain.util

sealed class OrderType{
    object Ascending: OrderType()//по возрастанию
    object Descending: OrderType()//по убыванию
}
