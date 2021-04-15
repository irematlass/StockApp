package com.app.mystockapp.api

import com.app.mystockapp.model.HandshakeRequest
import com.app.mystockapp.model.HandshakeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("api/handshake/start")
    @Headers("Content-Type: application/json")
    suspend fun requestHandshake(
        @Body request: HandshakeRequest
    ) : Response<HandshakeResponse>
}