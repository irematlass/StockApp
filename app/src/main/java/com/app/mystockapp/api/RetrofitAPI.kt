package com.app.mystockapp.api

import com.app.mystockapp.model.*
import retrofit2.Response
import retrofit2.http.*

interface RetrofitAPI {
    @POST("api/handshake/start")
    @Headers("Content-Type: application/json")
    suspend fun handshakeStart(
        @Body request: HandshakeRequest
    ): Response<HandshakeResponse>

    @POST("api/stocks/list")
    @Headers("Content-Type: application/json")
    suspend fun getStockList(
        @Header("X-VP-Authorization") XVPAuthorization: String,
        @Body request: StockRequest
    ): Response<StockResponse>

    @POST("api/stocks/detail")
    @Headers("Content-Type: application/json")
    suspend fun getStockDetail(
        @Header("X-VP-Authorization") XVPAuthorization: String,
        @Body request: DetailRequest
    ): Response<StockDetail>

}