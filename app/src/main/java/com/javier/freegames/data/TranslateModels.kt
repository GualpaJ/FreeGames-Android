package com.javier.freegames.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class TranslateRequest(
    val q: String,
    val source: String,
    val target: String,
    val format: String = "text"
)

data class TranslateResponse(
    val responseData: ResponseData
)

data class ResponseData(
    val translatedText: String
)

object TranslateApi {

    private const val BASE_URL = "https://api.mymemory.translated.net/"

    val service: TranslateService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TranslateService::class.java)
    }
}