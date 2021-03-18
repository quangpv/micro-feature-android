package com.example.authenticate

import com.example.core.lookup
import com.example.modules.authenticate.AuthenticateProxy
import com.example.modules.authenticate.UserModel
import com.example.modules.module.ModuleEvent

class AuthenticateGateway(override val emit: ModuleEvent) :
    AuthenticateProxy,
    AuthenticateEmitter {
    private val authorRepo: AuthorRepository by lookup()

    override suspend fun getUserLoggedIn(): UserModel {
        val name = authorRepo.getUserLogin()
        if (name.isBlank()) return UserModel.Empty
        return object : UserModel {
            override val userName: String = name
        }
    }

    override suspend fun logout() {
        authorRepo.logout()
    }
}