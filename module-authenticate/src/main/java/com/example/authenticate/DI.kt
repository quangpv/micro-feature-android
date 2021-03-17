package com.example.authenticate

import com.example.core.module
import com.example.modules.authenticate.AuthenticateProxy

val authenticateModules = module {
    factory { get<AuthenticateProxy>() as AuthenticateEmitter }

    single<AuthorRepository> { AuthorRepositoryImpl(get()) }
}