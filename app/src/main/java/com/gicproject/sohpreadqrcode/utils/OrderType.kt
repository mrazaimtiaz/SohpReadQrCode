package com.gicproject.sohpreadqrcode.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
