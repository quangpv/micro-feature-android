package com.example.modules.configure

interface ConfigProxy {
    val settings: ConfigSettings

    suspend fun loadConfig(): ConfigModel
}