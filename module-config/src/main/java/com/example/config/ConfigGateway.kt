package com.example.config

import com.example.core.lookup
import com.example.modules.configure.ConfigModel
import com.example.modules.configure.ConfigProxy
import com.example.modules.configure.ConfigSettings
import kotlinx.coroutines.delay

class ConfigGateway(override val settings: ConfigSettings) : ConfigProxy {
    private val configRepo: ConfigRepository by lookup()

    override suspend fun loadConfig(): ConfigModel {
        val config = configRepo.getConfig()
        delay(1000)
        return ConfigModelImpl(config)
    }
}
