package ru.simple.note.components.domain.util

sealed class OrderProperties(val orderType: OrderType) {
    class Title(orderType: OrderType): OrderProperties(orderType)//сортируем по заголовку
    class Date(orderType: OrderType): OrderProperties(orderType)//сортируем по дате
    class Color(orderType: OrderType): OrderProperties(orderType)//сортируем по цвету
}