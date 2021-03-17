package com.example.config

import kotlin.random.Random

interface ConfigRepository {
    suspend fun getConfig(): String
}

class ConfigRepositoryImpl : ConfigRepository {
    override suspend fun getConfig(): String {
        return Random.nextInt().toString()
    }
}