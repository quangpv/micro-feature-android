package com.example.authenticate

import com.example.core.module
import com.example.modules.module.ModuleProxy

val authenticateModules = module {
    factory { get<ModuleProxy>().authenticate as AuthenticateEmitter }

    single<AuthorRepository> { AuthorRepositoryImpl(get()) }
}