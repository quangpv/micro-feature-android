package com.example.simpleapp.modules

import com.example.config.ConfigGateway
import com.example.config.configModules
import com.example.core.gateway
import com.example.modules.configure.ConfigProxy

val configGateway = gateway<ConfigProxy>(configModules) {
    ConfigGateway(get())
}