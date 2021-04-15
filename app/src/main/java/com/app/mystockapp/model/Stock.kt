package com.app.mystockapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stockTable")
data class Stock(
    @ColumnInfo(name = "bid")
    val bid: Double,
    @ColumnInfo(name = "difference")
    val difference: Double,
   @PrimaryKey @ColumnInfo(name = "stock_id") @SerializedName("id")
    var uid: Int,
    @ColumnInfo(name = "isDown")
    val isDown: Boolean,
    @ColumnInfo(name = "isUp")
    val isUp: Boolean,
    @ColumnInfo(name = "offer")
    val offer: Double,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "volume")
    val volume: Double
)

