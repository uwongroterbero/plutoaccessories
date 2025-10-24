package com.example.plutoaccessories.network;

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
private const val BASE_URL = "http://10.0.2.2/pluto_api/" // untuk konek ke XAMPP

val instance: Retrofit by lazy {
    Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
}
