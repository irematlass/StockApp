package com.app.mystockapp.model

data class HandshakeResponse(
    val aesIV: String,
    val aesKey: String,
    val authorization: String,
    val lifeTime: String,
    val status: Status
)
