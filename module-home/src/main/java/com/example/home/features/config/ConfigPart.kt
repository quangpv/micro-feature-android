package com.example.home.features.config

import com.example.core.BodyPart
import com.example.core.Validate

class ConfigPart(private val value: String) : BodyPart {
    override val validate: Validate =
        if (value.isBlank()) Validate.fail("Config blank") else Validate.ok()

    override fun build(): Map<String, Any> {
        return mapOf("config" to value)
    }
}