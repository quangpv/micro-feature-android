package com.example.modules.module

import com.example.modules.authenticate.AuthenticateProxy
import com.example.modules.configure.ConfigProxy
import com.example.modules.home.HomeProxy

interface ModuleProxy {
    val home: HomeProxy

    val config: ConfigProxy

    val authenticate: AuthenticateProxy
}
