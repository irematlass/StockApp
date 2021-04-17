package com.app.mystockapp.repo

import androidx.lifecycle.LiveData
import com.app.mystockapp.api.RetrofitAPI
import com.app.mystockapp.db.StockDao
import com.app.mystockapp.model.*
import com.app.mystockapp.util.Resource
import java.lang.RuntimeException

import javax.inject.Inject

class StockRepository @Inject constructor(
    private val stockDao:StockDao,
    private val retrofitAPI: RetrofitAPI
) {
    suspend fun insertStocks(stock:Stock){
        stockDao.insertStocks(stock)
    }
    suspend fun deleteStocks(stock:Stock){
        stockDao.deleteStocks(stock)
    }
    fun getStocks(searchWord:String):LiveData<List<Stock>>{
        return stockDao.searchStocks(searchWord)
    }
    suspend fun handshake(request: HandshakeRequest):Resource<HandshakeResponse> {
        return try {
            val response=retrofitAPI.handshakeStart(request)
            if (response.isSuccessful){
                response.body()?.let{
                    return@let Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error("No data!",null)

        }
    }
    suspend fun getStockList(authorizationKey:String,period:StockRequest):Resource<StockResponse>{
        return try {
            val response=retrofitAPI.getStockList(authorizationKey,period)
            if (response.isSuccessful){
                response.body()?.let{
                    return@let Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error(e.toString(),null)

        }
    }
    suspend fun getStockDetail(authorizationKey:String,id:DetailRequest):Resource<StockDetail>{
        return try {
            val response=retrofitAPI.getStockDetail(authorizationKey,id)
            if (response.isSuccessful){
                response.body()?.let{
                    return@let Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error("No data!",null)

        }
    }

}