package com.app.mystockapp.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mystockapp.model.*
import com.app.mystockapp.repo.StockRepository
import com.app.mystockapp.util.AES
import com.app.mystockapp.util.Resource
import kotlinx.coroutines.launch
import java.lang.Exception


class StockViewModel @ViewModelInject constructor(
    private val repository: StockRepository
) : ViewModel() {


    var handshakeResponse = MutableLiveData<Resource<HandshakeResponse>>()

    private val stocks = MutableLiveData<Resource<List<Stock>>>()
    val stockList: LiveData<Resource<List<Stock>>>
        get() = stocks

    private val stockDetail = MutableLiveData<Resource<StockDetail>>()
    val stockdetail: LiveData<Resource<StockDetail>>
        get() = stockDetail

    //Home Fragment authentication method
    fun hanshakeRequest(request: HandshakeRequest) = viewModelScope.launch {
        val response = repository.handshake(request)
        handshakeResponse.value = response
    }

    fun getStockList(key: String, period: StockRequest,aeskey:String,aesıv:String) = viewModelScope.launch {
        try {


      var response = repository.getStockList(key, period)
        if (response.data!!.status.isSuccess){
            storeInSQLite(response.data!!.stocks,aeskey,aesıv)
        }
        var stockList=repository.getStocks()
        stocks.postValue(Resource.success(stockList))
        }catch (e:Exception){
            stocks.postValue(Resource.error("An error occurred, please try again: ${e.message}",
                emptyList()))
        }
    }

    fun getStockDetail(key: String, id: DetailRequest) = viewModelScope.launch {
        stockDetail.value = repository.getStockDetail(key, id)
    }
    fun getFilterStocks(word:String)=viewModelScope.launch {
        stocks.postValue(Resource.success(repository.getStocksFilter(word)))
    }
    fun getStocks()=viewModelScope.launch {
        stocks.postValue(Resource.success(repository.getStocks()))
    }
    private fun storeInSQLite(stock:List<Stock>,aeskey:String,aesıv:String){
        viewModelScope.launch{
            //repository.deleteStocks()
            stock.forEach {
               var value=AES.decrypt(it.symbol,aeskey,aesıv)
                Log.d("decrypt",value)
                it.symbol=value
                var temp=repository.insertStocks(it)
            }

        }
    }
}