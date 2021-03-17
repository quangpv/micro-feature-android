package com.example.authenticate

import android.content.Context

interface AuthorRepository {
    suspend fun getUserLogin(): String
    suspend fun login(userName: String)
    suspend fun logout()
}

class AuthorRepositoryImpl(context: Context) : AuthorRepository {
    private val shared = context.getSharedPreferences("test", Context.MODE_PRIVATE)

    override suspend fun getUserLogin(): String {
        return shared.getString("user", "") ?: ""
    }

    override suspend fun login(userName: String) {
        shared.edit().putString("user", userName).apply()
    }

    override suspend fun logout() {
        shared.edit().remove("user").apply()
    }
}