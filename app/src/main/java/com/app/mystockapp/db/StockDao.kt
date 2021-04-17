package com.app.mystockapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.mystockapp.model.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(stock:Stock)

    @Delete
    suspend fun deleteStocks(stock:Stock)

    @Query("SELECT * FROM stockTable  WHERE symbol LIKE '%' ||:searchWord|| '%'")
    fun searchStocks(searchWord:String): LiveData<List<Stock>>

}