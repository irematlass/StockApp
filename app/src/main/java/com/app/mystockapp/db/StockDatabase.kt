package com.app.mystockapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.mystockapp.model.Stock

@Database(entities = [Stock::class], version = 1)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockdao(): StockDao
}