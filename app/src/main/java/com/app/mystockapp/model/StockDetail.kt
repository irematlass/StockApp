package com.app.mystockapp.model

data class StockDetail (

    val bid: Double,
    val channge: Double,
    val count: Int,
    val difference: Double,
    val graphicData: List<Graphic>,
    val highest: Double,
    val isDown: Boolean,
    val isUp: Boolean,
    val lowest: Double,
    val maximum: Double,
    val minimum: Double,
    val offer: Double,
    val price: Double,
    val status: Status,
    val symbol: String,
    val volume: Double
)
data class Graphic (
    val day: Int,
    val value: Double
)
data class Status(
    val error: Error,
    val isSuccess: Boolean
)

data class Error (
    val code: Int,
    val message: String
)