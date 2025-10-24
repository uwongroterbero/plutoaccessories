package com.example.plutoaccessories.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Ganti dengan URL API kamu, misalnya localhost (kalau pakai emulator gunakan 10.0.2.2)
    private const val BASE_URL = "http://10.0.2.2/pluto_api/"

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
