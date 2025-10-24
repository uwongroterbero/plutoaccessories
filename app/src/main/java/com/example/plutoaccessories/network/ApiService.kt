package com.example.plutoaccessories.network

import com.example.plutoaccessories.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // 🔹 Register user
    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String,
        @Field("no_hp") noHp: String
    ): Call<ApiResponse>

    // 🔹 Login user
    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    // 🔹 Ambil daftar user (khusus admin)
    @GET("get_users.php")
    fun getAllUsers(): Call<List<UserModel>>
}
