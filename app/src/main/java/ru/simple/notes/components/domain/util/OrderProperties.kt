package ru.simple.notes.components.domain.util

sealed class OrderProperties(val orderType: OrderType) {
    class Title(orderType: OrderType) : OrderProperties(orderType)//сортируем по заголовку
    class Date(orderType: OrderType) : OrderProperties(orderType)//сортируем по дате
    class Color(orderType: OrderType) : OrderProperties(orderType)//сортируем по цвету

    fun copy(orderType: OrderType): OrderProperties =
        when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
}