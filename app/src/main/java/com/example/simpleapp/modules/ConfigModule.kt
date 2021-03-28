package com.example.simpleapp.modules

import com.example.config.ConfigGateway
import com.example.config.configModules
import com.example.core.proxyModule
import com.example.modules.configure.ConfigProxy

val configGateway = proxyModule<ConfigProxy>(configModules) {
    ConfigGateway(get())
}