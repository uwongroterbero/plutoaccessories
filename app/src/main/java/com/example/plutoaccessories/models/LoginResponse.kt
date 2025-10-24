package com.example.plutoaccessories.models

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: UserModel?
)
