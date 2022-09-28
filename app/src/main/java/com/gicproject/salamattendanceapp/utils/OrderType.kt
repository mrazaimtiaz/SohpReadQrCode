package com.gicproject.salamattendanceapp.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
