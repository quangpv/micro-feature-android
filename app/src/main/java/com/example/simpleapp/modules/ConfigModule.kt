package com.example.simpleapp.modules

import com.example.config.ConfigGateway
import com.example.config.configModules
import com.example.core.gatewayModule
import com.example.modules.configure.ConfigProxy

val configGateway = gatewayModule<ConfigProxy>(configModules) {
    ConfigGateway(get())
}