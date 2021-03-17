package com.example.home.features.config

import com.example.core.Mediator
import com.example.modules.configure.ConfigModel
import com.example.modules.module.ModuleProxy

class LoadConfigContract(private val proxy: ModuleProxy) : Mediator.Contract {

    object Load : Mediator.Command
    class NewConfig(val config: ConfigModel) : Mediator.Command

    override suspend fun invoke(command: Mediator.Command) = when (command) {
        is Load -> NewConfig(proxy.config.loadConfig())
        else -> Mediator.Empty
    }
}