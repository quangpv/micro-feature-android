package com.example.simpleapp.modules.author

import com.example.authenticate.AuthenticateProxyImpl
import com.example.authenticate.authenticateModules
import com.example.core.module
import com.example.modules.authenticate.AuthenticateProxy

val authAppModule = module {
    single<AuthenticateProxy> { AuthenticateProxyImpl(AuthorCoordinator()) }

    modules(authenticateModules)
}