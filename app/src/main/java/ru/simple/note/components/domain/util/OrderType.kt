package ru.simple.note.components.domain.util

sealed class OrderType {
    object Ascending : OrderType()//сортируем по возрастанию
    object Descending : OrderType()//сортируем по убыванию
}
