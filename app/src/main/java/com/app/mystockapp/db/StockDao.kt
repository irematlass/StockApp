package com.app.mystockapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.mystockapp.model.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(stock: Stock)

    @Query("DELETE FROM stockTable")
    suspend fun deleteStocks()

    @Query("SELECT * FROM stockTable  WHERE symbol LIKE '%' ||:searchWord|| '%'")
    suspend fun searchStocks(searchWord: String): List<Stock>

    @Query("SELECT * FROM stockTable")
    suspend fun getStocks(): List<Stock>
}