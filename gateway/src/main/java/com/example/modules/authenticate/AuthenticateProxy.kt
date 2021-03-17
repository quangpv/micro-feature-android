package com.example.modules.authenticate

interface AuthenticateProxy {
    suspend fun getUserLoggedIn(): UserModel
    suspend fun logout()
}