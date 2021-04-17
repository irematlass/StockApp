package com.app.mystockapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mystockapp.model.*
import com.app.mystockapp.repo.StockRepository
import com.app.mystockapp.util.Resource
import kotlinx.coroutines.launch


class StockViewModel @ViewModelInject constructor(
    private val repository: StockRepository
):ViewModel() {
      var handshakeResponse= MutableLiveData<Resource<HandshakeResponse>>()

    private val stocks= MutableLiveData<Resource<StockResponse>>()
    val stockList:LiveData<Resource<StockResponse>>
     get() = stocks

    private val stockDetail= MutableLiveData<Resource<StockDetail>>()
    val stockdetail:LiveData<Resource<StockDetail>>
        get() = stockDetail
    //Home Fragment authentication method
    fun hanshakeRequest(request: HandshakeRequest)=viewModelScope.launch {
        val response=repository.handshake(request)
        handshakeResponse.value=response
    }

    fun getStockList(key:String,period:StockRequest)=viewModelScope.launch {
        stocks.value=repository.getStockList(key,period)
    }
    fun getStockDetail(key:String,id:DetailRequest)=viewModelScope.launch {
        stockDetail.value=repository.getStockDetail(key,id)
    }
}